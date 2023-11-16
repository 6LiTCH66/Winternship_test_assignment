public class Player {
    // Player ID – A random UUID
    private String playerId;

    // Player operation – One of 3 operations: DEPOSIT, BET, WITHDRAW.
    private Operation operation;

    // Match ID – A random UUID.
    private String matchId;

    // The coin number player use for that operation.
    // This field is represent how much does the player bet or how much does the player spend on the bet
    private long betNumber;

    // The side of the match the player places the bet on.
    private String coinSiteNumber;

    public Player(String line){
        String[] split = line.split(",");
        this.playerId = split[0];
        this.operation = Operation.valueOf(split[1]);
        this.matchId = split[2];
        this.betNumber = Long.parseLong(split[3]);
        this.coinSiteNumber = ((split.length < 5) ? "" : split[4]);

    }


    public String getPlayerId() {
        return playerId;
    }

    public Operation getOperation() {
        return operation;
    }

    public String getMatchId() {
        return matchId;
    }

    public long getBetNumber() {
        return betNumber;
    }

    public String getCoinSiteNumber() {
        return coinSiteNumber;
    }

    @Override
    public String toString() {
        return this.playerId + " " + this.operation + " " + ((this.matchId.isEmpty()) ? null : this.matchId) + " " + this.betNumber + " " + ((this.coinSiteNumber.isEmpty()) ? null : this.coinSiteNumber);
    }
}
