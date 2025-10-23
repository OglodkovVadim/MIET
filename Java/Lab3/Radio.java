package Lab3;
public class Radio
{
    protected String currentStation;
    protected Boolean status;
    
    public Radio(String currentStation, Boolean status) {
        this.currentStation = currentStation;
        this.status = status;
    }
    public void PlayTunes() {
        System.out.println("Playing...");
    }
    public void SetCurrenStation(String newCurrentStation) {
        currentStation = newCurrentStation;
    }
    public void SetStatus(Boolean newStatus) {
        status = newStatus;
    } 
}