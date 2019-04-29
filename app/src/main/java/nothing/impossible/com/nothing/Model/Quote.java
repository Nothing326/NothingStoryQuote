package nothing.impossible.com.nothing.Model;

/**
 * Created by User on 4/23/18.
 */
public class Quote {
    String id ;
    String detail;
    String author;
    String image;
    String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDetailEng() {
        return detailEng;
    }

    public void setDetailEng(String detailEng) {
        this.detailEng = detailEng;
    }

    String detailEng;
    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public   Quote(){

    }
  public  Quote(String id,String detail,String detailEng,String author,String image,String role){
        this.id = id;
        this.detailEng = detailEng;
        this.detail=detail;
        this.author=author;
        this.image = image;
        this.role = role;
    }
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
    private boolean isSelected;

    public void setSelected(boolean selection, boolean b){
        this.isSelected = selection;
    }

    public boolean isSelected(){
        return isSelected;
    }
    public void setSelected(boolean selection){
        this.isSelected = selection;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
