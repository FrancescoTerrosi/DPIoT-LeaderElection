package ms;

import org.apache.commons.math3.random.RandomGenerator;
import org.cmg.ml.sam.sim.*;		
import eu.quanticol.carma.simulator.*;
import eu.quanticol.carma.simulator.space.Location;
import eu.quanticol.carma.simulator.space.Node;
import eu.quanticol.carma.simulator.space.SpaceModel;
import eu.quanticol.carma.simulator.space.Tuple;
import eu.quanticol.carma.simulator.space.Edge;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.TreeSet;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;
import org.cmg.ml.sam.sim.sampling.*;


public class AllTheWayMinAct extends CarmaModel {
	
	public AllTheWayMinAct() {
	}
	

	


	
	
	
	
	
	public String[] getSystems() {
		return new String[] {
		};	
	}
	
	public SimulationFactory<CarmaSystem> getFactory( String name ) {
		return null;
	}
			
	
	
		
		public String[] getMeasures() {
			TreeSet<String> sortedSet = new TreeSet<String>( );
			return sortedSet.toArray( new String[ sortedSet.size() ] );
		}
		
		public Measure<CarmaSystem> getMeasure( String name , Map<String,Object> parameters ) {
			return null;
		}
	
		public String[] getMeasureParameters( String name ) {
			return new String[] {};
		}
		
		public Map<String,Class<?>> getParametersType( String name ) {
			return new HashMap<>();
		}
		
	
		
	
}
