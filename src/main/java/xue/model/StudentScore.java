/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2018年4月12日
 */
package xue.model;

/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2018年4月12日
 */
public class StudentScore {
    private String name;
    private String id;
    private String rank;
    private String yuwne;
    private String shuxue;
    private String english;
    private String shengwu;
    private String wuli;
    private String huaxue;
    private String rankone;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getYuwne() {
        return yuwne;
    }

    public void setYuwne(String yuwne) {
        this.yuwne = yuwne;
    }

    public String getShuxue() {
        return shuxue;
    }

    public void setShuxue(String shuxue) {
        this.shuxue = shuxue;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getShengwu() {
        return shengwu;
    }

    public void setShengwu(String shengwu) {
        this.shengwu = shengwu;
    }

    public String getWuli() {
        return wuli;
    }

    public void setWuli(String wuli) {
        this.wuli = wuli;
    }

    public String getHuaxue() {
        return huaxue;
    }

    public void setHuaxue(String huaxue) {
        this.huaxue = huaxue;
    }

    public String getRankone() {
        return rankone;
    }

    public void setRankone(String rankone) {
        this.rankone = rankone;
    }

    public StudentScore(String name, String id, String rank, String yuwne, String shuxue, String english,
            String shengwu, String wuli, String huaxue, String rankone) {
        super();
        this.name = name;
        this.id = id;
        this.rank = rank;
        this.yuwne = yuwne;
        this.shuxue = shuxue;
        this.english = english;
        this.shengwu = shengwu;
        this.wuli = wuli;
        this.huaxue = huaxue;
        this.rankone = rankone;
    }

}
