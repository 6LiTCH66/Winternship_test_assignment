import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

public class Result {
    private String playerId;
    private long balance;
    private double winRate;


    public Result(String playerId){
        this.playerId = playerId;
        this.balance = 0;
        this.winRate = 0;
    }


    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public double getWinRate() {
        return winRate;
    }

    public void setWinRate(double winRate) {
        this.winRate = winRate;
    }
    // This method formatted win rate so the win rate look like this in the result.txt file: 0,14, not like this 0.14
    private String formattedWinRate(){
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        DecimalFormat decimalFormat = new DecimalFormat("#.##", symbols);
        return decimalFormat.format(this.winRate);
    }

    @Override
    public String toString() {
        return this.playerId + " " + this.balance + " " + formattedWinRate();
    }
}
