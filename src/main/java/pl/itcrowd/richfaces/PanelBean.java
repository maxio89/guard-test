package pl.itcrowd.richfaces;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Piotr Kozlowski
 * Date: 2/28/13
 * Company: IT Crowd
 */
@ManagedBean
@RequestScoped
public class PanelBean implements Serializable {

    private String text;

    private Random generator = new Random();

    public void changeText() throws InterruptedException {
        String a = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        String b = "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb";
        if (text.equals(a)) {
            text = b;
        } else {
            text = a;
        }
        int ms = generator.nextInt(600) + 1;
        Thread.sleep(ms);
    }

    public int getRandomInterval() {
        return generator.nextInt(1000) + 100;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
