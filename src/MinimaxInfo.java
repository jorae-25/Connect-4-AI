public class MinimaxInfo {
    private int bestAction;
    private int miniMaxValue;

    public MinimaxInfo(int mxValue, int bAction){
        this.bestAction =bAction;
        this.miniMaxValue = mxValue;
    }
    public int getBestAction(){
        return bestAction;
    }
    public int getMiniMaxValue(){
        return miniMaxValue;
    }
}
