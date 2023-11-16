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

    @Override
    public String toString() {
        return this.playerId + " " + this.balance + " " + this.winRate;
    }
}
