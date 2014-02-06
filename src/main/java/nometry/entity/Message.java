package nometry.entity;

public class Message
{
    private String message;

    public Message() {}
    
    public Message(String message)
    {
        this.message = message;
    }
    
    public String toString()
    {
        return message;
    }
    
    public byte[] getBytes()
    {
        return message.getBytes();
    }
    
    public Message setMessageString(String message)
    {
        this.message = message + "\0";
        return this;
    }
}
