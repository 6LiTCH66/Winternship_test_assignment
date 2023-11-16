public class Match {
    private String matchId;
    private float rateOfA;
    private float rateOfB;
    private String matchResult;

    public Match(String lines){
        String[] split = lines.split(",");
        this.matchId = split[0];

//        setMatchId(split[0]);
        this.rateOfA = Float.parseFloat(split[1]);
//        setRateOfA(Float.parseFloat(split[1]));
        this.rateOfB = Float.parseFloat(split[2]);
//        setRateOfB(Float.parseFloat(split[2]));
        this.matchResult = split[3];
//        setMatchResult(split[3]);

    }

    public String getMatchId() {
        return matchId;
    }

    public float getRateOfA() {
        return rateOfA;
    }

    public float getRateOfB() {
        return rateOfB;
    }

    public String getMatchResult() {
        return matchResult;
    }

}
