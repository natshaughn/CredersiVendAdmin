package routes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.credersi_vend.routes.nodes.CustomerNode;
import com.credersi_vend.routes.nodes.DomainNode;
import com.credersi_vend.routes.nodes.MachineNode;
import com.credersi_vend.routes.nodes.SiteNode;
import com.credersi_vend.routes.rels.OwnsRel;
import com.credersi_vend.routes.rels.RouteRel;
import com.credersi_vend.routes.rels.SuppliesRel;

public class RoutesTests {

	@Test
	public void domainNodeEnvironmentTest() {
		DomainNode myNode = new DomainNode();
		myNode.environment("Chrome");
		assertEquals(myNode.getEnvironment(), "Chrome");
	}

	@Test
	public void domainNodeLabelTest() {
		DomainNode myNode = new DomainNode();	
		assertEquals(myNode.getLabel(), "Domain");
	}
	
	@Test
	public void domainNodeNameTest() {
		DomainNode myNode = new DomainNode();
		myNode.name("RoVR");
		assertEquals(myNode.getName(), "RoVR");
	}
	
	@Test
	public void customerNodeNameTest() {
		CustomerNode myNode = new CustomerNode();
		myNode.name("Jack");
		
//		myNode.name("RoVR");
		assertEquals(myNode.getName(), "Jack");
	}
	
	@Test
	public void machineNodeLocationTest() {
		MachineNode myNode = new MachineNode();
		myNode.location("downstairs").name("Machine One");
		
		assertEquals(myNode.getLocation(), "downstairs");
		assertEquals(myNode.getName(), "Machine One");
	}
	
	@Test
	public void siteNodeAddressTest() {
		SiteNode myNode = new SiteNode();
		myNode.address("Third Floor");
		
		assertEquals(myNode.getAddress(), "Third Floor");
	}
	
	@Test
	public void siteNodeOriginTest() {
		SiteNode myNode = new SiteNode();
		myNode.origin();
		
		assertTrue(myNode.getOrigin());
	}
	
	@Test
	public void siteNodeNameTest() {
		SiteNode myNode = new SiteNode();
		myNode.setName("test");
		
		assertEquals(myNode.getName(), "test");
	}
	
	@Test
	public void siteNodeLabelTest() {
		SiteNode myNode = new SiteNode();
		
		assertEquals(myNode.getLabel(), "Site");
	}
	
	@Test
	public void ownsRelLabelTest() {
		OwnsRel myRel = new OwnsRel();
		assertEquals(myRel.getLabel(), "OWNS");
	}
	
	@Test
	public void routeRelDirectionsTest() {
		RouteRel myRoute = new RouteRel();
		myRoute.directions("North");
		
		assertEquals(myRoute.getDirections(), "North");		
	}
	
	@Test
	public void routeRelGetLabel() {
		RouteRel myRoute = new RouteRel();
		assertEquals(myRoute.getLabel(), "ROUTE");
	}
	
	@Test
	public void suppliesRelGetLabel() {
		SuppliesRel mySupplies = new SuppliesRel();
		assertEquals(mySupplies.getLabel(), "SUPPLIES");
	}
		
}
