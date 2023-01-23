package in.cdac.university.globalService.util.html;

public class Style {
    
    private String styleString = "";

    @Override
    public String toString() {
        return " style='white-space: pre-line" + styleString + "'";
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

    public Style flex(String flex) {
        if (flex != null)
            styleString += ";flex: " + flex;
        return this;
    }
}
