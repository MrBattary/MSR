package michael.linker.msr;


import michael.linker.msr.Main;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Inheritance of this class allows to raise the context only once,
 * which significantly speeds up the testing process.
 */
@SpringBootTest()
@ContextConfiguration(classes = Main.class)
public abstract class ParentTest {
}
