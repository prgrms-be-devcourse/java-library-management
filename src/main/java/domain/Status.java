package domain;

public enum Status {
    AVAILABLE("대여 가능"), BORROWED("대여중"), CLEANING("정리중"), LOST("분실");
    private final String label;
    Status(String label){
        this.label = label;
    }
    public String getLabel(){
        return label;
    }

}