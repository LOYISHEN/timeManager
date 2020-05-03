package timeManager.Plan;

public class PlanContent {
    String content;

    public PlanContent() {
        this.content = new String();
    }

    public PlanContent(String content) {
        this.content = content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public String toString() {
        return this.content;
    }
}
