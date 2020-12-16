package modele.plateau;

abstract class Gameplay {
    private int score;
    public void setScore(int val){
        score = val;
    }
    public void getscore(){
        System.out.println(score);
    }
}
