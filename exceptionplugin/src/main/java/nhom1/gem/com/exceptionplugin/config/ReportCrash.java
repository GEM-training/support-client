package nhom1.gem.com.exceptionplugin.config;

/**
 * Created by vanhop on 3/11/16.
 */
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Created by VanHop on 3/2/2016.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.TYPE })
public @interface ReportCrash {
    public String formUri() default "http://172.16.10.80:8080";
}