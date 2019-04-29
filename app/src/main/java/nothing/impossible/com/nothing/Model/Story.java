package nothing.impossible.com.nothing.Model;

/**
 * Created by User on 12/23/17.
 */
public class Story {
    private String title,image,storyDetail,storyDetailEng,titleEng,id;
    private int checked;
    public int type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static final int TEXT_TYPE=0;

    public String getTitleEng() {
        return titleEng;
    }

    public void setTitleEng(String titleEng) {
        this.titleEng = titleEng;
    }

    public String getStoryDetailEng() {
        return storyDetailEng;
    }

    public void setStoryDetailEng(String storyDetailEng) {
        this.storyDetailEng = storyDetailEng;
    }

    public static final int IMAGE_TYPE=1;
    public static final int IMAGE_VERTICAL_TYPE=2;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

   public Story(int type,String title, String image, String storyDetail){
        this.title=title;
        this.image=image;
        this.storyDetail=storyDetail;
        this.type=type;
    }

    public String getStoryDetail() {
        return storyDetail;
    }

    public void setStoryDetail(String storyDetail) {
        this.storyDetail = storyDetail;
    }

    public Story(){

    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
