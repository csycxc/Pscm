
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.banry.pscm.ApplicationStartup;
import com.banry.pscm.PscmWebApplication;
import com.banry.pscm.persist.dds.DynamicDataSourceContextHolder;
import com.banry.pscm.service.engsafety.EngsafetyException;
import com.banry.pscm.service.engsafety.HiddenTroubleBillService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PscmWebApplication.class)
@ContextConfiguration(classes = {ApplicationStartup.class})
public class HiddenTroubleBillServiceImplTest {

	@Autowired
    private HiddenTroubleBillService hiddenTroubleBillService;
    
	@Test
	public void testFindHiddenTroubleBillsByDivItemCode() {
		try {
			DynamicDataSourceContextHolder.set("chenjuan");
			hiddenTroubleBillService.findHiddenTroubleBillsByDivItemCode("111", "company_t5");
		} catch (EngsafetyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
