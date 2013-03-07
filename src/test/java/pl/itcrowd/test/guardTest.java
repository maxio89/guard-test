package pl.itcrowd.test;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.enricher.findby.FindBy;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pl.itcrowd.richfaces.PanelBean;
import pl.itcrowd.richfaces.PollBean;

import java.io.File;
import java.net.URL;

import static org.jboss.arquillian.graphene.Graphene.guardXhr;

/**
 * Created with IntelliJ IDEA.
 * User: Piotr Kozlowski
 * Date: 2/28/13
 * Company: IT Crowd
 */

@RunWith(Arquillian.class)
public class guardTest {

    private static final String WEBAPP_SRC = "src/main/webapp";

    @Drone
    WebDriver browser;

    @ArquillianResource
    URL deploymentUrl;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {

        File[] libs = Maven.resolver()
                .loadPomFromFile("pom.xml")
                .resolve("org.jboss.spec.javax.el:jboss-el-api_2.2_spec:1.0.2.Final",
                        "org.richfaces.core:richfaces-core-api:4.3.0.Final",
                        "org.richfaces.core:richfaces-core-impl:4.3.0.Final",
                        "org.richfaces.ui:richfaces-components-api:4.3.0.Final",
                        "org.richfaces.ui:richfaces-components-ui:4.3.0.Final",
                        "org.jboss.spec.javax.faces:jboss-jsf-api_2.1_spec:2.0.1.Final",
                        "com.sun.faces:jsf-impl:2.1.7-jbossorg-2")
                .withTransitivity()
                .asFile();


        return ShrinkWrap.create(WebArchive.class, "login.war")
                .addClasses(PanelBean.class, PollBean.class)
                .addAsLibraries(libs)
                .merge(ShrinkWrap.create(GenericArchive.class).as(ExplodedImporter.class)
                        .importDirectory(WEBAPP_SRC).as(GenericArchive.class),
                        "/", Filters.includeAll());

    }


    @FindBy(id = "f:btn1")
    private WebElement btn1;

    @FindBy(id = "f:btn2")
    private WebElement btn2;

   /**
    *  This test shows that if user forgets use guard, another guards fail
    * a4j:poll may be disabled.
    *
    * */
    @Test
    public void firstTest() {
        browser.get(deploymentUrl + "home.jsf");

        guardXhr(btn1).click();
        btn2.click();
        guardXhr(btn1).click();
        guardXhr(btn2).click();

    }

    /**
     *  This test shows that if are Xhr requests in the background (using a4j:poll)
     *  guards sometime fail.
     *
     *  I also joined to project screen shot with strange exception.
     *
     * */
    @Test
    public void secondTest() {
        browser.get(deploymentUrl + "home.jsf");

        guardXhr(btn1).click();
        guardXhr(btn2).click();
        guardXhr(btn1).click();
        guardXhr(btn2).click();
        guardXhr(btn1).click();
        guardXhr(btn2).click();
        guardXhr(btn1).click();
        guardXhr(btn2).click();
        guardXhr(btn1).click();
        guardXhr(btn2).click();
        guardXhr(btn1).click();
        guardXhr(btn2).click();

    }

}