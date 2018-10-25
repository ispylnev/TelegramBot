import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Arrays;
import java.util.Collection;


public class Test1 {



        @RunWith(Parameterized.class)
        class Test2{
            int a;
            int b;
            int exp;

            public Test2(int a, int b, int exp) {
                this.a = a;
                this.b = b;
                this.exp = exp;
            }

            @Parameterized.Parameters
            public Collection number(){
                return Arrays.asList(new Object[][] {{1,2,3},{1,4,5},{1,1,2}});
            }

//            Param param = new Param();
            @Test
            public void test(){
                int res = param.sum(a,b);
                assertEquals(exp,res);


            }


        }
    }

}
