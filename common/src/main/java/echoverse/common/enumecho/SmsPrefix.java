package echoverse.common.enumecho;

public enum SmsPrefix {

    REGISTER("register"),
    LOGIN("login");

    private SmsPrefix(String prefix){
        this.prefix = prefix;
    }

    private String prefix;

    public String getPrefix(){
        return this.prefix;
    }
}