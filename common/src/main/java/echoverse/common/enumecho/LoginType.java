package echoverse.common.enumecho;

public enum LoginType {

    PASSWORD("password"),
    CODE("code");

    private LoginType(String type){
        this.type=type;
    }

    private String type;

    public String getType(){
        return this.type;
    }
}
