package in.cdac.university.globalService.util.html;

public class Style {
    
    private String styleString = "";

    @Override
    public String toString() {
        return " style='white-space: pre-line; word-wrap: break-word; hyphens: auto" + styleString + "'";
    }
    
    private Style() {        
    }
    
    public static Style createStyle() {
        return new Style();
    }
    
    public Style alignment(String alignment) {
        if (alignment != null)
            styleString += ";text-align: " + alignment;
        return this;
    }
    
    public Style fontWeight(String weight) {
        if (weight != null)
            styleString += ";font-weight: " + weight;
        return this;
    }

    public Style fontSize(String fontSize) {
        if (fontSize != null)
            styleString += ";font-size: " + fontSize;
        return this;
    }

    public Style padding(String padding) {
        if (padding != null)
            styleString += ";padding: " + padding;
        return this;
    }

    public Style paddingTop(String paddingTop) {
        if (paddingTop != null)
            styleString += ";padding-top: " + paddingTop;
        return this;
    }

    public Style paddingBottom(String paddingBottom) {
        if (paddingBottom != null)
            styleString += ";padding-bottom: " + paddingBottom;
        return this;
    }

    public Style paddingLeft(String paddingLeft) {
        if (paddingLeft != null)
            styleString += ";padding-left: " + paddingLeft;
        return this;
    }
    
    public Style paddingRight(String paddingRight) {
        if (paddingRight != null)
            styleString += ";padding-right: " + paddingRight;
        return this;
    }

    public Style display(String display) {
        if (display != null)
            styleString += ";display: " + display;
        return this;
    }

    public Style flex(String flex) {
        if (flex != null)
            styleString += ";flex: " + flex;
        return this;
    }

    public Style textDecoration(String textDecoration) {
        if (textDecoration != null)
            styleString += ";text-decoration: " + textDecoration;
        return this;
    }

    public Style background(String background) {
        if (background != null)
            styleString += ";background: " + background;
        return this;
    }

    public Style height(String height) {
        if (height != null)
            styleString += ";height: " + height;
        return this;
    }

    public Style width(String width) {
        if (width != null)
            styleString += ";width: " + width;
        return this;
    }

    public Style borderRight(String borderRight) {
        if (borderRight != null)
            styleString += ";border-right: " + borderRight;
        return this;
    }

    public Style borderLeft(String borderLeft) {
        if (borderLeft != null)
            styleString += ";border-left: " + borderLeft;
        return this;
    }

    public Style marginTop(String marginTop) {
        if (marginTop != null)
            styleString += ";margin-top: " + marginTop;
        return this;
    }
}
