import java.io.*;
import java.util.*;
import java.util.function.Function;

public class Main {
    public static <T> void readFile(String filePath, List<T> list, Function<String, T> converter){
        try{
            File myObj = new File(filePath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                T item = converter.apply(data);
                list.add(item);
            }
            myReader.close();

        }catch (FileNotFoundException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static void writeFile(String data){
        String fileLocation = "src/result.txt";

        try{
            FileWriter fileWriter = new FileWriter(fileLocation, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(data);


            bufferedWriter.close();
            fileWriter.close();
        }catch (IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public static void clearFile(){
        String fileLocation = "src/result.txt";
        File file = new File(fileLocation);
        if (file.exists()) {
            file.delete();

        }

    }

    public static void calculateBalance(Result result, List<Match> matches, List<Player> playerActions, List<Result> legitimateActions, List<Player> illegalActions, List<Long> casinoBalanceList){

        int placedBets = 0;
        int wonGames = 0;

        long casinoHostBalance = 0;

        for (Player player: playerActions){
            if ((player.getOperation().equals(Operation.BET) || player.getOperation().equals(Operation.WITHDRAW)) && (result.getBalance() < player.getBetNumber())){
                illegalActions.add(player);
                return;
            }
            switch (player.getOperation()){
                case DEPOSIT:
                    result.setBalance((long) (result.getBalance() + player.getBetNumber()));
                    break;
                case BET:
                    // counting placed bets
                    placedBets++;
                    // looping through the match array to find matches that are connected to the current player
                    for (Match match: matches){
                        // finding math for current player
                        if (player.getMatchId().equals(match.getMatchId())){
                            // comparing match result with current player's coin bet
                            // if current player won the game
                            if (player.getCoinSiteNumber().equals(match.getMatchResult())){
                                // counting won game
                                wonGames++;
                                // calculating winnings
                                float calculatedBetResult = calculateBet(match, player.getBetNumber(), player.getCoinSiteNumber());
                                // since player won we are subtracting winnings from casino balance
                                casinoHostBalance = (casinoHostBalance - (int)calculatedBetResult);

                                result.setBalance((long) (result.getBalance() + calculatedBetResult));

                            }else{
                                // if it is a draw we don't do anything, otherwise we add bet that player has lost to casino balance and subtracting this bet from player's balance
                                if (!match.getMatchResult().equals("DRAW")){
                                    casinoHostBalance = casinoHostBalance + player.getBetNumber();
                                    result.setBalance((long) (result.getBalance() - player.getBetNumber()));
                                }
                            }

                        }
                    }
                    break;

                case WITHDRAW:
                    result.setBalance((long) (result.getBalance() - player.getBetNumber()));
                    break;
            }

        }

        result.setWinRate(calculateWinRate(wonGames, placedBets));

        legitimateActions.add(result);

        casinoBalanceList.add(casinoHostBalance);

    }


    public static double calculateWinRate(int wonGames, int placedBets){
        return Math.floor(((float) wonGames / placedBets) * 100) / 100;
    }

    public static float calculateBet(Match match, long betNumber, String coinSite){
        // bet size * corresponding side rate
        return switch (coinSite) {
            case "A" -> betNumber * match.getRateOfA();
            case "B" -> betNumber * match.getRateOfB();
            default -> betNumber;
        };
    }



    public static void calculateResult(HashMap<String, List<Player>> playerHashMap, List<Match> matches){
        // List of the all legitimate actions
        List<Result> legitimateActions = new ArrayList<>();
        // List of the all illegal actions
        List<Player> illegalActions = new ArrayList<>();
        // List of casino balance left after each player
        List<Long> casinoBalanceList = new ArrayList<>();

        // Looping through player hashmap where 'key' is player ID and 'value' is player's actions
        for (String playerId : playerHashMap.keySet()) {
            calculateBalance(new Result(playerId), matches, playerHashMap.get(playerId), legitimateActions, illegalActions, casinoBalanceList);
        }

        long casinoBalance = casinoBalanceList.stream().mapToLong(Long::longValue).sum();

        writeResultToFile(legitimateActions, illegalActions, casinoBalance);

    }

    public static void writeResultToFile(List<Result> legitimateActions, List<Player> illegalActions, long casinoBalance){
        clearFile();
        if (!legitimateActions.isEmpty()){
            for (Result legitimate: legitimateActions){
                writeFile(legitimate.toString() + "\n");
            }
        }else {
            writeFile("\n");
        }

        writeFile("\n");

        if (!illegalActions.isEmpty()){
            for (Player illegal: illegalActions){
                writeFile(illegal.toString() + "\n");
            }
        }else{
            writeFile("\n");
        }

        writeFile("\n");

        writeFile(Long.toString(casinoBalance));
    }

    public static void main(String[] args) {
        List<Player> playerActions = new ArrayList<>();
        List<Match> matches = new ArrayList<>();

        HashMap<String, List<Player>> playerHashMap = new HashMap<>();

        readFile("player_data.txt", playerActions, Player::new);
        readFile("match_data.txt", matches, Match::new);

        // Just creating hashmap of player
        for (Player player: playerActions){
            if (!playerHashMap.containsKey(player.getPlayerId())){
                playerHashMap.put(player.getPlayerId(), new ArrayList<>());
                playerHashMap.get(player.getPlayerId()).add(player);
            }else{
                playerHashMap.get(player.getPlayerId()).add(player);
            }

        }

        calculateResult(playerHashMap, matches);


    }


}