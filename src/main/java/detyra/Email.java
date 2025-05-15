package detyra;

public class Email {
    private String from;
    private String to;
    private String content;
    private String signature;

    public Email(String from, String to, String content, String signature) {
        this.from = from;
        this.to = to;
        this.content = content;
        this.signature = signature;
    }

    public String getFrom() { return from; }
    public String getTo() { return to; }
    public String getContent() { return content; }
    public String getSignature() { return signature; }
}
