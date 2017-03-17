package xpoint.engine.script;

/**
 * Created by Spark Zhu<szhu.spark@gmail.com> on 07/12/2016.
 */
public class Scope {
    public final Long from;
    public final Long to;
    public final String lang;
    public final String[] companies;
    public final String credential;

    public Scope(String credential, Long from, Long to, String lang, String[] companies) {
        this.from = from;
        this.to = to;
        this.lang = lang;
        this.companies = companies;
        this.credential = credential;
    }
}
