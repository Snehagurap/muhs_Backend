package in.cdac.university.globalService.util.html;

public class Tag {

    private String tagString;

    private String tagName;

    private boolean isEndBracketAdded = false;

    @Override
    public String toString() {
        if (isEndBracketAdded)
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
        isEndBracketAdded = true;
        tagString += ">" + (text == null ? "" : text);
        return this;
    }

    public Tag endTag() {
        isEndBracketAdded = true;
        tagString += "</" + tagName + ">";
        return this;
    }

    public Tag endTagOnly() {
        tagString = "</" + tagName + ">";
        return this;
    }

    public Tag addTag(Tag tag) {
        if (!isEndBracketAdded)
            tagString += ">";
        tagString += tag.toString();
        isEndBracketAdded = true;
        return this;
    }
}
