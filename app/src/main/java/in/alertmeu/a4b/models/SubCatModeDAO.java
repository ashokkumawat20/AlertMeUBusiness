package in.alertmeu.a4b.models;


public class SubCatModeDAO {
    private String id;
    private String bc_id;
    private String subcategory_name;
    String subcategory_name_hindi;
    String checked_status;
    String activeads;
    String isselected = "";
    String image_path="";
    private String sequence = "";

    private boolean isSelected = false;


    public SubCatModeDAO() {

    }

    public SubCatModeDAO(String id,String bc_id, String subcategory_name) {
        this.id = id;
        this.bc_id = bc_id;
        this.subcategory_name = subcategory_name;

    }

    public SubCatModeDAO(String id,String bc_id, String subcategory_name, boolean isSelected,String image_path) {
        this.id = id;
        this.bc_id = bc_id;
        this.subcategory_name = subcategory_name;
        this.isSelected = isSelected;
        this.image_path = image_path;
    }

    public SubCatModeDAO(String id, String bc_id, String subcategory_name, String checked_status, String isselected, String sequence, boolean isSelected) {
        this.id = id;
        this.bc_id = bc_id;
        this.subcategory_name = subcategory_name;
        this.checked_status = checked_status;
        this.isselected = isselected;
        this.sequence = sequence;
        this.isSelected = isSelected;
    }

    public SubCatModeDAO(String id, String bc_id, String subcategory_name, String checked_status, String activeads, String isselected, String sequence, boolean isSelected) {
        this.id = id;
        this.bc_id = bc_id;
        this.subcategory_name = subcategory_name;
        this.checked_status = checked_status;
        this.activeads = activeads;
        this.isselected = isselected;
        this.sequence = sequence;
        this.isSelected = isSelected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBc_id() {
        return bc_id;
    }

    public void setBc_id(String bc_id) {
        this.bc_id = bc_id;
    }

    public String getSubcategory_name() {
        return subcategory_name;
    }

    public void setSubcategory_name(String subcategory_name) {
        this.subcategory_name = subcategory_name;
    }

    public String getChecked_status() {
        return checked_status;
    }

    public void setChecked_status(String checked_status) {
        this.checked_status = checked_status;
    }

    public String getIsselected() {
        return isselected;
    }

    public void setIsselected(String isselected) {
        this.isselected = isselected;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getActiveads() {
        return activeads;
    }

    public void setActiveads(String activeads) {
        this.activeads = activeads;
    }

    public String getSubcategory_name_hindi() {
        return subcategory_name_hindi;
    }

    public void setSubcategory_name_hindi(String subcategory_name_hindi) {
        this.subcategory_name_hindi = subcategory_name_hindi;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    @Override
    public String toString() {
        return subcategory_name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SubCatModeDAO) {
            SubCatModeDAO c = (SubCatModeDAO) obj;
            if (c.getSubcategory_name().equals(subcategory_name) && c.getId() == id) return true;
        }

        return false;
    }
}