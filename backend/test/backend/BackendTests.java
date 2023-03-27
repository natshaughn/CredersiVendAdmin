package backend;

import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
import org.junit.Test;

import com.credersi_vend.backend.routes.nodes.SiteRequest;
import com.credersi_vend.backend.routes.nodes.CustomerRequest;
import com.credersi_vend.backend.routes.nodes.DomainRequest;
import com.credersi_vend.backend.routes.nodes.MachineRequest;

public class BackendTests {

	@Test
	public void customerRequestTest() {
		CustomerRequest newCustomer = new CustomerRequest();
		newCustomer.setName("Tom");
		
		assertEquals(newCustomer.getName(), "Tom");
	}
	
	@Test
	public void domainRequestTest() {
		DomainRequest newDomain = new DomainRequest("Chrome", "One");
		
		assertEquals(newDomain.getEnvironment(), "Chrome");
		assertEquals(newDomain.getName(), "One");
	}
	
	@Test
	public void machineRequestTest() {
		MachineRequest newMachine = new MachineRequest("North", "Chorley", "Machine One");
		
		assertEquals(newMachine.getDirections(), "North");
		assertEquals(newMachine.getLocation(),"Chorley");
		assertEquals(newMachine.getName(), "Machine One");
	}
	
	@Test
	public void siteRequestTest() {
		SiteRequest newSite = new SiteRequest("Assurance House", "RoVR");
		
		assertEquals(newSite.getAddress(), "Assurance House");
		assertEquals(newSite.getName(), "RoVR");
	}	
}

