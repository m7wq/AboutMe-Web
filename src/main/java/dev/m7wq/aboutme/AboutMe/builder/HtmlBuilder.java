package dev.m7wq.aboutme.AboutMe.builder;


public class HtmlBuilder {

    private final StringBuilder builder = new StringBuilder();

    public HtmlBuilder(String string){
        builder.append(string);
    }

    public HtmlBuilder(){}

    public HtmlBuilder span(String text){
        builder.append("<span>%s</span>".formatted(text));
        return this;
    }

    @Override
    public String toString(){
        return builder.toString();
    }

    public HtmlBuilder appendBackButton(){
        builder.append("""
                         <form>
                                <button type="button" onclick="window.history.back();">
                                    Go Back
                                </button>
                            </form>
                         """);

        return this;
    }

    public HtmlBuilder pre(String textArea){
        builder.append("<pre>")
                .append(textArea)
                .append("</pre>");
        return this;
    }

    public HtmlBuilder b(){
        builder.append("<b>");
         return this;
    }

    public HtmlBuilder bEnd(){
        builder.append("</b>");
        return this;
    }

    public HtmlBuilder br(){
        builder.append("<br>");
        return this;
    }

    public HtmlBuilder h(int level, String label){
        builder.append(
                        "<h%s>%s</h%s>"
                                .formatted(level, label, level)
                );
        return this;
    }
}
