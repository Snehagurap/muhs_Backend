package in.cdac.university.globalService.util.html;

public class Tag {

    private String tagString;

    private String tagName;

    private boolean isEndTagAdded = false;

    @Override
    public String toString() {
        if (isEndTagAdded)
            return tagString;
        return tagString + ">";
    }

    private Tag() {
    }

    public static Tag createTag(String tagName) {
        Tag tag = new Tag();
        tag.tagName = tagName;
        tag.tagString = "<" + tagName;
        return tag;
    }

    public Tag addStyle(Style style) {
        tagString += style;
        return this;
    }

    public Tag addText(String text) {
        tagString += ">" + (text == null ? "" : text);
        return this;
    }

    public Tag endTag() {
        isEndTagAdded = true;
        tagString += "</" + tagName + ">";
        return this;
    }

    public Tag addTag(Tag tag) {
        System.out.println("Tag to be added");
        System.out.println(tag.toString());
        tagString += tag.toString();
        return this;
    }
}
