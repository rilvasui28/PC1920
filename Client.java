class Client {
    
    private String username;
    private String password;
    private int pwcorona;
    private boolean loggedstate;

    public Client(String username, String password){
        this.username= username;
        this.password = password;
        this.pwcorona = -1;
        this.loggedstate = false;
    }

    public String getUser(){
        return this.username;
    }

    public String getPass(){
        return this.password;
    }

    public int getCount(){
        return this.pwcorona;
    }

    public boolean getStatus(){
        return this.loggedstate;
    }

    public void setCount(int n){
        this.pwcorona = n;
    }

    public void setStatus(Boolean ls){
        this.loggedstate = ls;
    }
}