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
		generateAgentBehaviour( );
	}
	

	
	public static class __RECORD__ToForward implements Cloneable {
		
		public Integer __FIELD__ID;
		public Integer __FIELD__COUNT;
		
		public __RECORD__ToForward( Integer __FIELD__ID,Integer __FIELD__COUNT) {
			this.__FIELD__ID = __FIELD__ID;
			this.__FIELD__COUNT = __FIELD__COUNT;
		}
	
		public __RECORD__ToForward( __RECORD__ToForward record ) {
			this.__FIELD__ID = record.__FIELD__ID;
			this.__FIELD__COUNT = record.__FIELD__COUNT;
		}
		
		public String toString() {
			return "[ "+"ID="+__FIELD__ID+" , "+"COUNT="+__FIELD__COUNT+" ]";
		}
		
		public boolean equals( Object o ) {
			if (o instanceof __RECORD__ToForward) {
				__RECORD__ToForward other = (__RECORD__ToForward) o;
				return 
				this.__FIELD__ID.equals( other.__FIELD__ID )					&&
				this.__FIELD__COUNT.equals( other.__FIELD__COUNT )					
						;	
			}	
			return false;
		}
		
		public __RECORD__ToForward clone() {
			return new __RECORD__ToForward( this );
		}
	}

	public final int __CONST__NODEZ = 50;

	public LinkedList<__RECORD__ToForward> __FUN__removeFirstElement ( 
		LinkedList<__RECORD__ToForward> __VARIABLE__pending
	) {
		{
			//
			LinkedList<__RECORD__ToForward> __VARIABLE__result =new LinkedList<__RECORD__ToForward>()
			;
			//
			//
			if (carmaEquals( computeSize( __VARIABLE__pending ) , 1 )) {
				//
				return __VARIABLE__result;
				//
			}
			//
			//
			for( int __VARIABLE__i = 1 ; __VARIABLE__i < computeSize( __VARIABLE__pending ) ; __VARIABLE__i += 1 ) 
				{
					//
					__VARIABLE__result = concatenate( __VARIABLE__result , getList( get(__VARIABLE__pending,__VARIABLE__i) )  );
					//
				}
			//
			//
			return __VARIABLE__result;
			//
		}
	}
	public __RECORD__ToForward __FUN__addElement ( 
		Integer __VARIABLE__id,Integer __VARIABLE__counter
	) {
		{
			//
			__RECORD__ToForward __VARIABLE__result =new __RECORD__ToForward( Integer.valueOf( __VARIABLE__id ),
			Integer.valueOf( __VARIABLE__counter )
			 );
			//
			//
			return __VARIABLE__result.clone();
			//
		}
	}
	
	
	
	/* START COMPONENT: Agent         */
	
	/* DEFINITIONS OF PROCESSES */
	public final CarmaProcessAutomaton _COMP_Agent = new CarmaProcessAutomaton("Agent");
	
	public final CarmaProcessAutomaton.State __STATE___Agent_IDLE = _COMP_Agent.newState("IDLE");		
	public final CarmaProcessAutomaton.State __STATE___Agent_ACTIVE = _COMP_Agent.newState("ACTIVE");		
	public final CarmaProcessAutomaton.State __STATE___Agent_CHECK = _COMP_Agent.newState("CHECK");		
	public final CarmaProcessAutomaton.State __STATE___Agent_LEADER = _COMP_Agent.newState("LEADER");		
	public final CarmaProcessAutomaton.State __STATE___Agent_FOLLOWER = _COMP_Agent.newState("FOLLOWER");		
	
	private void generateAgentBehaviour( ) {
		
		
		{
			CarmaAction action = new CarmaOutput(
				__ACT_NAME__send , __ACT__send , false  		
			) {
				
				@Override
				protected Object getValue(CarmaSystem sys, CarmaStore store, final double now) {
					LinkedList<Object> toReturn = new LinkedList<Object>();
					final Node __MY__loc = store.get( "loc" , Node.class );
					final Node __ATTR__loc = store.get( "loc" , Node.class );
					Integer __MY__id = (Integer) store.get( "id" );
					toReturn.add( __MY__id );
					toReturn.add( 1 );
					return toReturn;
				}
				
				@Override
				protected CarmaStoreUpdate getUpdate(CarmaSystem sys,  final double now ) {
					return new CarmaStoreUpdate() {
						
						//@Override
						public void update(RandomGenerator r, CarmaStore store) {
							final Node __MY__loc = store.get( "loc" , Node.class );
							final Node __ATTR__loc = store.get( "loc" , Node.class );
							Integer __MY__id = (Integer) store.get( "id" );
							store.set( "leader", __MY__id );
						}
					};
				}
				
				@Override
				protected CarmaPredicate getPredicate(CarmaSystem sys, final CarmaStore myStore) {
					final Node __MY__loc = myStore.get( "loc" , Node.class );
					Integer __MY__right = (Integer) myStore.get( "right" );
					return new CarmaPredicate() {
			
						//@Override
						public boolean satisfy(double now,CarmaStore store) {
							try {
								Node __ATTR__loc = store.get( "loc" , Node.class );
								Integer __ATTR__zone = (Integer) store.get( "zone" );
								return carmaEquals( __MY__right , __ATTR__zone );
							} catch (NullPointerException e) {
								return false;
							}
						}
						
					};
					
				}
			};		
			
			_COMP_Agent.addTransition( 
				__STATE___Agent_IDLE , 
				action , 
				__STATE___Agent_ACTIVE );			
		}
		{
			CarmaAction action = new CarmaInput( 
				__ACT_NAME__send , __ACT__send , false  		
			) {
				
				@Override
				protected CarmaStoreUpdate getUpdate(CarmaSystem sys, final Object value, final double now) {
					
					LinkedList<Object> message = (LinkedList<Object>) value;
					final int __VARIABLE__anId = (Integer) message.get(0);
					final int __VARIABLE__aCounter = (Integer) message.get(1);
					return new CarmaStoreUpdate() {
						
						//@Override
						public void update(RandomGenerator r, CarmaStore store) {
							LinkedList<__RECORD__ToForward> __ATTR__pending = (LinkedList<__RECORD__ToForward>) store.get( "pending" );
							Node __MY__loc = store.get( "loc" , Node.class );
							Node __ATTR__loc = store.get( "loc" , Node.class );
							store.set( "pending", concatenate( __ATTR__pending , getList( __FUN__addElement( 
										Integer.valueOf( __VARIABLE__anId ),
										Integer.valueOf( __VARIABLE__aCounter )
									).clone() )  ) );
							__ATTR__pending = concatenate( __ATTR__pending , getList( __FUN__addElement( 
										Integer.valueOf( __VARIABLE__anId ),
										Integer.valueOf( __VARIABLE__aCounter )
									).clone() )  );
						}
					};
								
				}	
				
				@Override
				protected CarmaPredicate getPredicate(CarmaSystem sys, CarmaStore myStore, Object value) {
							LinkedList<Object> message = (LinkedList<Object>) value;
							final int __VARIABLE__anId = (Integer) message.get(0);
							final int __VARIABLE__aCounter = (Integer) message.get(1);
							final Node __MY__loc = myStore.get( "loc" , Node.class );
							Integer __MY__left = (Integer) myStore.get( "left" );
							return new CarmaPredicate() {
			
								//@Override
								public boolean satisfy(double now,CarmaStore store) {
									try {
										Node __ATTR__loc = store.get( "loc" , Node.class );
										Integer __ATTR__zone = (Integer) store.get( "zone" );
										return carmaEquals( __MY__left , __ATTR__zone );
									} catch (NullPointerException e) {
										return false;
									}
								}
								
							};
					
				}
							
			};		
			
			_COMP_Agent.addTransition( 
				__STATE___Agent_IDLE , 
				action , 
				__STATE___Agent_FOLLOWER );			
		}
		{
			CarmaAction action = new CarmaInput( 
				__ACT_NAME__send , __ACT__send , false  		
			) {
				
				@Override
				protected CarmaStoreUpdate getUpdate(CarmaSystem sys, final Object value, final double now) {
					
					LinkedList<Object> message = (LinkedList<Object>) value;
					final int __VARIABLE__anId = (Integer) message.get(0);
					final int __VARIABLE__aCounter = (Integer) message.get(1);
					return new CarmaStoreUpdate() {
						
						//@Override
						public void update(RandomGenerator r, CarmaStore store) {
							LinkedList<__RECORD__ToForward> __ATTR__pending = (LinkedList<__RECORD__ToForward>) store.get( "pending" );
							Node __MY__loc = store.get( "loc" , Node.class );
							Node __ATTR__loc = store.get( "loc" , Node.class );
							store.set( "pending", concatenate( __ATTR__pending , getList( __FUN__addElement( 
										Integer.valueOf( __VARIABLE__anId ),
										Integer.valueOf( __VARIABLE__aCounter )
									).clone() )  ) );
							__ATTR__pending = concatenate( __ATTR__pending , getList( __FUN__addElement( 
										Integer.valueOf( __VARIABLE__anId ),
										Integer.valueOf( __VARIABLE__aCounter )
									).clone() )  );
						}
					};
								
				}	
				
				@Override
				protected CarmaPredicate getPredicate(CarmaSystem sys, CarmaStore myStore, Object value) {
							LinkedList<Object> message = (LinkedList<Object>) value;
							final int __VARIABLE__anId = (Integer) message.get(0);
							final int __VARIABLE__aCounter = (Integer) message.get(1);
							final Node __MY__loc = myStore.get( "loc" , Node.class );
							Integer __MY__left = (Integer) myStore.get( "left" );
							return new CarmaPredicate() {
			
								//@Override
								public boolean satisfy(double now,CarmaStore store) {
									try {
										Node __ATTR__loc = store.get( "loc" , Node.class );
										Integer __ATTR__zone = (Integer) store.get( "zone" );
										return carmaEquals( __MY__left , __ATTR__zone );
									} catch (NullPointerException e) {
										return false;
									}
								}
								
							};
					
				}
							
			};		
			
			_COMP_Agent.addTransition( 
				__STATE___Agent_ACTIVE , 
				action , 
				__STATE___Agent_ACTIVE );			
		}
		{
			CarmaPredicate _FOO_predicate0 = new CarmaPredicate() {
		
				//@Override
				public boolean satisfy(double now,CarmaStore store) {
					final Node __MY__loc = store.get( "loc" , Node.class );
					final Node __ATTR__loc = store.get( "loc" , Node.class );
					LinkedList<__RECORD__ToForward> __ATTR__pending = (LinkedList<__RECORD__ToForward>) store.get( "pending" );
					LinkedList<__RECORD__ToForward> __MY__pending = (LinkedList<__RECORD__ToForward>) store.get( "pending" );
					Integer __MY__id = (Integer) store.get( "id" );
					return ( ( computeSize( __MY__pending ) )>( 0 ) )&&( carmaEquals( get(__ATTR__pending,0).__FIELD__ID , __MY__id ) );
				}
					
			};
			{
				CarmaAction action = new CarmaOutput(
					__ACT_NAME__check , __ACT__check , true  		
				) {
					
					@Override
					protected Object getValue(CarmaSystem sys, CarmaStore store, final double now) {
						LinkedList<Object> toReturn = new LinkedList<Object>();
						final Node __MY__loc = store.get( "loc" , Node.class );
						final Node __ATTR__loc = store.get( "loc" , Node.class );
						return toReturn;
					}
					
					@Override
					protected CarmaStoreUpdate getUpdate(CarmaSystem sys,  final double now ) {
						return new CarmaStoreUpdate() {
							
							//@Override
							public void update(RandomGenerator r, CarmaStore store) {
								final Node __MY__loc = store.get( "loc" , Node.class );
								final Node __ATTR__loc = store.get( "loc" , Node.class );
							}
						};
					}
					
					@Override
					protected CarmaPredicate getPredicate(CarmaSystem sys, final CarmaStore myStore) {
						return CarmaPredicate.FALSE;
						
					}
				};		
				
				_COMP_Agent.addTransition( 
					__STATE___Agent_ACTIVE , 
					new CarmaPredicate.Conjunction(  _FOO_predicate0  ) , 
					action , 
					__STATE___Agent_CHECK );			
			}
		}
		{
			CarmaPredicate _FOO_predicate0 = new CarmaPredicate() {
		
				//@Override
				public boolean satisfy(double now,CarmaStore store) {
					final Node __MY__loc = store.get( "loc" , Node.class );
					final Node __ATTR__loc = store.get( "loc" , Node.class );
					LinkedList<__RECORD__ToForward> __ATTR__pending = (LinkedList<__RECORD__ToForward>) store.get( "pending" );
					LinkedList<__RECORD__ToForward> __MY__pending = (LinkedList<__RECORD__ToForward>) store.get( "pending" );
					Integer __MY__id = (Integer) store.get( "id" );
					return ( ( computeSize( __MY__pending ) )>( 0 ) )&&( !( carmaEquals( get(__ATTR__pending,0).__FIELD__ID , __MY__id ) ) );
				}
					
			};
			{
				CarmaAction action = new CarmaOutput(
					__ACT_NAME__send , __ACT__send , false  		
				) {
					
					@Override
					protected Object getValue(CarmaSystem sys, CarmaStore store, final double now) {
						LinkedList<Object> toReturn = new LinkedList<Object>();
						LinkedList<__RECORD__ToForward> __ATTR__pending = (LinkedList<__RECORD__ToForward>) store.get( "pending" );
						final Node __MY__loc = store.get( "loc" , Node.class );
						final Node __ATTR__loc = store.get( "loc" , Node.class );
						toReturn.add( get(__ATTR__pending,0).__FIELD__ID );
						toReturn.add( ( get(__ATTR__pending,0).__FIELD__COUNT )+( 1 ) );
						return toReturn;
					}
					
					@Override
					protected CarmaStoreUpdate getUpdate(CarmaSystem sys,  final double now ) {
						return new CarmaStoreUpdate() {
							
							//@Override
							public void update(RandomGenerator r, CarmaStore store) {
								final Node __MY__loc = store.get( "loc" , Node.class );
								final Node __ATTR__loc = store.get( "loc" , Node.class );
								LinkedList<__RECORD__ToForward> __ATTR__pending = (LinkedList<__RECORD__ToForward>) store.get( "pending" );
								Integer __MY__leader = (Integer) store.get( "leader" );
								Integer __MY__sizy = (Integer) store.get( "sizy" );
								store.set( "leader", Math.min( get(__ATTR__pending,0).__FIELD__ID , __MY__leader ) );
								__MY__leader = Math.min( get(__ATTR__pending,0).__FIELD__ID , __MY__leader );
								store.set( "sizy", ( __MY__sizy )+( 1 ) );
								__MY__sizy = ( __MY__sizy )+( 1 );
								store.set( "pending", __FUN__removeFirstElement( 
											__ATTR__pending
										) );
								__ATTR__pending = __FUN__removeFirstElement( 
											__ATTR__pending
										);
							}
						};
					}
					
					@Override
					protected CarmaPredicate getPredicate(CarmaSystem sys, final CarmaStore myStore) {
						final Node __MY__loc = myStore.get( "loc" , Node.class );
						Integer __MY__right = (Integer) myStore.get( "right" );
						return new CarmaPredicate() {
				
							//@Override
							public boolean satisfy(double now,CarmaStore store) {
								try {
									Node __ATTR__loc = store.get( "loc" , Node.class );
									Integer __ATTR__zone = (Integer) store.get( "zone" );
									return carmaEquals( __MY__right , __ATTR__zone );
								} catch (NullPointerException e) {
									return false;
								}
							}
							
						};
						
					}
				};		
				
				_COMP_Agent.addTransition( 
					__STATE___Agent_ACTIVE , 
					new CarmaPredicate.Conjunction(  _FOO_predicate0  ) , 
					action , 
					__STATE___Agent_ACTIVE );			
			}
		}
		{
			CarmaPredicate _FOO_predicate0 = new CarmaPredicate() {
		
				//@Override
				public boolean satisfy(double now,CarmaStore store) {
					final Node __MY__loc = store.get( "loc" , Node.class );
					final Node __ATTR__loc = store.get( "loc" , Node.class );
					Integer __MY__sizy = (Integer) store.get( "sizy" );
					LinkedList<__RECORD__ToForward> __MY__pending = (LinkedList<__RECORD__ToForward>) store.get( "pending" );
					Integer __MY__leader = (Integer) store.get( "leader" );
					Integer __MY__id = (Integer) store.get( "id" );
					return ( carmaEquals( __MY__sizy , get(__MY__pending,0).__FIELD__COUNT ) )&&( carmaEquals( __MY__leader , __MY__id ) );
				}
					
			};
			{
				CarmaAction action = new CarmaOutput(
					__ACT_NAME__becomeLeader , __ACT__becomeLeader , true  		
				) {
					
					@Override
					protected Object getValue(CarmaSystem sys, CarmaStore store, final double now) {
						LinkedList<Object> toReturn = new LinkedList<Object>();
						final Node __MY__loc = store.get( "loc" , Node.class );
						final Node __ATTR__loc = store.get( "loc" , Node.class );
						return toReturn;
					}
					
					@Override
					protected CarmaStoreUpdate getUpdate(CarmaSystem sys,  final double now ) {
						return new CarmaStoreUpdate() {
							
							//@Override
							public void update(RandomGenerator r, CarmaStore store) {
								final Node __MY__loc = store.get( "loc" , Node.class );
								final Node __ATTR__loc = store.get( "loc" , Node.class );
								LinkedList<__RECORD__ToForward> __ATTR__pending = (LinkedList<__RECORD__ToForward>) store.get( "pending" );
								store.set( "pending", __FUN__removeFirstElement( 
											__ATTR__pending
										) );
								__ATTR__pending = __FUN__removeFirstElement( 
											__ATTR__pending
										);
							}
						};
					}
					
					@Override
					protected CarmaPredicate getPredicate(CarmaSystem sys, final CarmaStore myStore) {
						return CarmaPredicate.FALSE;
						
					}
				};		
				
				_COMP_Agent.addTransition( 
					__STATE___Agent_CHECK , 
					new CarmaPredicate.Conjunction(  _FOO_predicate0  ) , 
					action , 
					__STATE___Agent_LEADER );			
			}
		}
		{
			CarmaPredicate _FOO_predicate0 = new CarmaPredicate() {
		
				//@Override
				public boolean satisfy(double now,CarmaStore store) {
					final Node __MY__loc = store.get( "loc" , Node.class );
					final Node __ATTR__loc = store.get( "loc" , Node.class );
					Integer __MY__sizy = (Integer) store.get( "sizy" );
					LinkedList<__RECORD__ToForward> __MY__pending = (LinkedList<__RECORD__ToForward>) store.get( "pending" );
					Integer __MY__leader = (Integer) store.get( "leader" );
					Integer __MY__id = (Integer) store.get( "id" );
					return ( carmaEquals( __MY__sizy , get(__MY__pending,0).__FIELD__COUNT ) )&&( !( carmaEquals( __MY__leader , __MY__id ) ) );
				}
					
			};
			{
				CarmaAction action = new CarmaOutput(
					__ACT_NAME__becomeFollower , __ACT__becomeFollower , true  		
				) {
					
					@Override
					protected Object getValue(CarmaSystem sys, CarmaStore store, final double now) {
						LinkedList<Object> toReturn = new LinkedList<Object>();
						final Node __MY__loc = store.get( "loc" , Node.class );
						final Node __ATTR__loc = store.get( "loc" , Node.class );
						return toReturn;
					}
					
					@Override
					protected CarmaStoreUpdate getUpdate(CarmaSystem sys,  final double now ) {
						return new CarmaStoreUpdate() {
							
							//@Override
							public void update(RandomGenerator r, CarmaStore store) {
								final Node __MY__loc = store.get( "loc" , Node.class );
								final Node __ATTR__loc = store.get( "loc" , Node.class );
							}
						};
					}
					
					@Override
					protected CarmaPredicate getPredicate(CarmaSystem sys, final CarmaStore myStore) {
						return CarmaPredicate.FALSE;
						
					}
				};		
				
				_COMP_Agent.addTransition( 
					__STATE___Agent_CHECK , 
					new CarmaPredicate.Conjunction(  _FOO_predicate0  ) , 
					action , 
					__STATE___Agent_FOLLOWER );			
			}
		}
		{
			CarmaPredicate _FOO_predicate0 = new CarmaPredicate() {
		
				//@Override
				public boolean satisfy(double now,CarmaStore store) {
					final Node __MY__loc = store.get( "loc" , Node.class );
					final Node __ATTR__loc = store.get( "loc" , Node.class );
					Integer __MY__sizy = (Integer) store.get( "sizy" );
					LinkedList<__RECORD__ToForward> __MY__pending = (LinkedList<__RECORD__ToForward>) store.get( "pending" );
					return !( carmaEquals( __MY__sizy , get(__MY__pending,0).__FIELD__COUNT ) );
				}
					
			};
			{
				CarmaAction action = new CarmaOutput(
					__ACT_NAME__becomeFeederz , __ACT__becomeFeederz , true  		
				) {
					
					@Override
					protected Object getValue(CarmaSystem sys, CarmaStore store, final double now) {
						LinkedList<Object> toReturn = new LinkedList<Object>();
						final Node __MY__loc = store.get( "loc" , Node.class );
						final Node __ATTR__loc = store.get( "loc" , Node.class );
						return toReturn;
					}
					
					@Override
					protected CarmaStoreUpdate getUpdate(CarmaSystem sys,  final double now ) {
						return new CarmaStoreUpdate() {
							
							//@Override
							public void update(RandomGenerator r, CarmaStore store) {
								final Node __MY__loc = store.get( "loc" , Node.class );
								final Node __ATTR__loc = store.get( "loc" , Node.class );
							}
						};
					}
					
					@Override
					protected CarmaPredicate getPredicate(CarmaSystem sys, final CarmaStore myStore) {
						return CarmaPredicate.FALSE;
						
					}
				};		
				
				_COMP_Agent.addTransition( 
					__STATE___Agent_CHECK , 
					new CarmaPredicate.Conjunction(  _FOO_predicate0  ) , 
					action , 
					__STATE___Agent_ACTIVE );			
			}
		}
		{
			CarmaAction action = new CarmaInput( 
				__ACT_NAME__send , __ACT__send , false  		
			) {
				
				@Override
				protected CarmaStoreUpdate getUpdate(CarmaSystem sys, final Object value, final double now) {
					
					LinkedList<Object> message = (LinkedList<Object>) value;
					final int __VARIABLE__anId = (Integer) message.get(0);
					final int __VARIABLE__aCounter = (Integer) message.get(1);
					return new CarmaStoreUpdate() {
						
						//@Override
						public void update(RandomGenerator r, CarmaStore store) {
							LinkedList<__RECORD__ToForward> __ATTR__pending = (LinkedList<__RECORD__ToForward>) store.get( "pending" );
							Node __MY__loc = store.get( "loc" , Node.class );
							Node __ATTR__loc = store.get( "loc" , Node.class );
							store.set( "pending", concatenate( __ATTR__pending , getList( __FUN__addElement( 
										Integer.valueOf( __VARIABLE__anId ),
										Integer.valueOf( __VARIABLE__aCounter )
									).clone() )  ) );
							__ATTR__pending = concatenate( __ATTR__pending , getList( __FUN__addElement( 
										Integer.valueOf( __VARIABLE__anId ),
										Integer.valueOf( __VARIABLE__aCounter )
									).clone() )  );
						}
					};
								
				}	
				
				@Override
				protected CarmaPredicate getPredicate(CarmaSystem sys, CarmaStore myStore, Object value) {
							LinkedList<Object> message = (LinkedList<Object>) value;
							final int __VARIABLE__anId = (Integer) message.get(0);
							final int __VARIABLE__aCounter = (Integer) message.get(1);
							final Node __MY__loc = myStore.get( "loc" , Node.class );
							Integer __MY__left = (Integer) myStore.get( "left" );
							return new CarmaPredicate() {
			
								//@Override
								public boolean satisfy(double now,CarmaStore store) {
									try {
										Node __ATTR__loc = store.get( "loc" , Node.class );
										Integer __ATTR__zone = (Integer) store.get( "zone" );
										return carmaEquals( __MY__left , __ATTR__zone );
									} catch (NullPointerException e) {
										return false;
									}
								}
								
							};
					
				}
							
			};		
			
			_COMP_Agent.addTransition( 
				__STATE___Agent_CHECK , 
				action , 
				__STATE___Agent_CHECK );			
		}
		{
			CarmaAction action = new CarmaOutput(
				__ACT_NAME__beLeader , __ACT__beLeader , true  		
			) {
				
				@Override
				protected Object getValue(CarmaSystem sys, CarmaStore store, final double now) {
					LinkedList<Object> toReturn = new LinkedList<Object>();
					final Node __MY__loc = store.get( "loc" , Node.class );
					final Node __ATTR__loc = store.get( "loc" , Node.class );
					return toReturn;
				}
				
				@Override
				protected CarmaStoreUpdate getUpdate(CarmaSystem sys,  final double now ) {
					return new CarmaStoreUpdate() {
						
						//@Override
						public void update(RandomGenerator r, CarmaStore store) {
							final Node __MY__loc = store.get( "loc" , Node.class );
							final Node __ATTR__loc = store.get( "loc" , Node.class );
						}
					};
				}
				
				@Override
				protected CarmaPredicate getPredicate(CarmaSystem sys, final CarmaStore myStore) {
					return CarmaPredicate.FALSE;
					
				}
			};		
			
			_COMP_Agent.addTransition( 
				__STATE___Agent_LEADER , 
				action , 
				__STATE___Agent_LEADER );			
		}
		{
			CarmaAction action = new CarmaInput( 
				__ACT_NAME__send , __ACT__send , false  		
			) {
				
				@Override
				protected CarmaStoreUpdate getUpdate(CarmaSystem sys, final Object value, final double now) {
					
					LinkedList<Object> message = (LinkedList<Object>) value;
					final int __VARIABLE__anId = (Integer) message.get(0);
					final int __VARIABLE__aCounter = (Integer) message.get(1);
					return new CarmaStoreUpdate() {
						
						//@Override
						public void update(RandomGenerator r, CarmaStore store) {
							LinkedList<__RECORD__ToForward> __ATTR__pending = (LinkedList<__RECORD__ToForward>) store.get( "pending" );
							Node __MY__loc = store.get( "loc" , Node.class );
							Node __ATTR__loc = store.get( "loc" , Node.class );
							store.set( "pending", concatenate( __ATTR__pending , getList( __FUN__addElement( 
										Integer.valueOf( __VARIABLE__anId ),
										Integer.valueOf( __VARIABLE__aCounter )
									).clone() )  ) );
							__ATTR__pending = concatenate( __ATTR__pending , getList( __FUN__addElement( 
										Integer.valueOf( __VARIABLE__anId ),
										Integer.valueOf( __VARIABLE__aCounter )
									).clone() )  );
						}
					};
								
				}	
				
				@Override
				protected CarmaPredicate getPredicate(CarmaSystem sys, CarmaStore myStore, Object value) {
							LinkedList<Object> message = (LinkedList<Object>) value;
							final int __VARIABLE__anId = (Integer) message.get(0);
							final int __VARIABLE__aCounter = (Integer) message.get(1);
							final Node __MY__loc = myStore.get( "loc" , Node.class );
							Integer __MY__left = (Integer) myStore.get( "left" );
							return new CarmaPredicate() {
			
								//@Override
								public boolean satisfy(double now,CarmaStore store) {
									try {
										Node __ATTR__loc = store.get( "loc" , Node.class );
										Integer __ATTR__zone = (Integer) store.get( "zone" );
										return carmaEquals( __MY__left , __ATTR__zone );
									} catch (NullPointerException e) {
										return false;
									}
								}
								
							};
					
				}
							
			};		
			
			_COMP_Agent.addTransition( 
				__STATE___Agent_FOLLOWER , 
				action , 
				__STATE___Agent_FOLLOWER );			
		}
		{
			CarmaPredicate _FOO_predicate0 = new CarmaPredicate() {
		
				//@Override
				public boolean satisfy(double now,CarmaStore store) {
					final Node __MY__loc = store.get( "loc" , Node.class );
					final Node __ATTR__loc = store.get( "loc" , Node.class );
					LinkedList<__RECORD__ToForward> __ATTR__pending = (LinkedList<__RECORD__ToForward>) store.get( "pending" );
					LinkedList<__RECORD__ToForward> __MY__pending = (LinkedList<__RECORD__ToForward>) store.get( "pending" );
					Integer __MY__id = (Integer) store.get( "id" );
					return ( ( computeSize( __MY__pending ) )>( 0 ) )&&( !( carmaEquals( get(__ATTR__pending,0).__FIELD__ID , __MY__id ) ) );
				}
					
			};
			{
				CarmaAction action = new CarmaOutput(
					__ACT_NAME__send , __ACT__send , false  		
				) {
					
					@Override
					protected Object getValue(CarmaSystem sys, CarmaStore store, final double now) {
						LinkedList<Object> toReturn = new LinkedList<Object>();
						LinkedList<__RECORD__ToForward> __ATTR__pending = (LinkedList<__RECORD__ToForward>) store.get( "pending" );
						final Node __MY__loc = store.get( "loc" , Node.class );
						final Node __ATTR__loc = store.get( "loc" , Node.class );
						toReturn.add( get(__ATTR__pending,0).__FIELD__ID );
						toReturn.add( get(__ATTR__pending,0).__FIELD__COUNT );
						return toReturn;
					}
					
					@Override
					protected CarmaStoreUpdate getUpdate(CarmaSystem sys,  final double now ) {
						return new CarmaStoreUpdate() {
							
							//@Override
							public void update(RandomGenerator r, CarmaStore store) {
								final Node __MY__loc = store.get( "loc" , Node.class );
								final Node __ATTR__loc = store.get( "loc" , Node.class );
								LinkedList<__RECORD__ToForward> __ATTR__pending = (LinkedList<__RECORD__ToForward>) store.get( "pending" );
								Integer __MY__leader = (Integer) store.get( "leader" );
								store.set( "leader", Math.min( get(__ATTR__pending,0).__FIELD__ID , __MY__leader ) );
								__MY__leader = Math.min( get(__ATTR__pending,0).__FIELD__ID , __MY__leader );
								store.set( "pending", __FUN__removeFirstElement( 
											__ATTR__pending
										) );
								__ATTR__pending = __FUN__removeFirstElement( 
											__ATTR__pending
										);
							}
						};
					}
					
					@Override
					protected CarmaPredicate getPredicate(CarmaSystem sys, final CarmaStore myStore) {
						final Node __MY__loc = myStore.get( "loc" , Node.class );
						Integer __MY__right = (Integer) myStore.get( "right" );
						return new CarmaPredicate() {
				
							//@Override
							public boolean satisfy(double now,CarmaStore store) {
								try {
									Node __ATTR__loc = store.get( "loc" , Node.class );
									Integer __ATTR__zone = (Integer) store.get( "zone" );
									return carmaEquals( __MY__right , __ATTR__zone );
								} catch (NullPointerException e) {
									return false;
								}
							}
							
						};
						
					}
				};		
				
				_COMP_Agent.addTransition( 
					__STATE___Agent_FOLLOWER , 
					new CarmaPredicate.Conjunction(  _FOO_predicate0  ) , 
					action , 
					__STATE___Agent_FOLLOWER );			
			}
		}
		
	}
	
	public CarmaComponent createComponentAgent( 
		Integer __VARIABLE__zone  
	) {
		CarmaComponent c = new CarmaComponent();
		c.setName( "Agent" );
		Integer __ATTR__id;
		Integer __MY__id;
		__ATTR__id =  __VARIABLE__zone;
		__MY__id = __ATTR__id;
		c.set( "id" ,  __ATTR__id );
		Integer __ATTR__sizy;
		Integer __MY__sizy;
		__ATTR__sizy =  1;
		__MY__sizy = __ATTR__sizy;
		c.set( "sizy" ,  __ATTR__sizy );
		Integer __ATTR__zone;
		Integer __MY__zone;
		__ATTR__zone =  __VARIABLE__zone;
		__MY__zone = __ATTR__zone;
		c.set( "zone" ,  __ATTR__zone );
		Integer __ATTR__right;
		Integer __MY__right;
		__ATTR__right =  ( ( __ATTR__zone )+( 1 ) )%( __CONST__NODEZ );
		__MY__right = __ATTR__right;
		c.set( "right" ,  __ATTR__right );
		Integer __ATTR__left;
		Integer __MY__left;
		__ATTR__left =  ( ( ( __ATTR__zone )+( __CONST__NODEZ ) )-( 1 ) )%( __CONST__NODEZ );
		__MY__left = __ATTR__left;
		c.set( "left" ,  __ATTR__left );
		LinkedList<__RECORD__ToForward> __ATTR__pending;
		LinkedList<__RECORD__ToForward> __MY__pending;
		__ATTR__pending =  new LinkedList<__RECORD__ToForward>()
		;
		__MY__pending = __ATTR__pending;
		c.set( "pending" ,  __ATTR__pending );
		Integer __ATTR__leader;
		Integer __MY__leader;
		__ATTR__leader =  Integer.MAX_VALUE;
		__MY__leader = __ATTR__leader;
		c.set( "leader" ,  __ATTR__leader );
		Boolean __ATTR__done;
		Boolean __MY__done;
		__ATTR__done =  false;
		__MY__done = __ATTR__done;
		c.set( "done" ,  __ATTR__done );
		c.addAgent( new CarmaSequentialProcess( c , _COMP_Agent , __STATE___Agent_IDLE ));
		return c;
	}	
	
	/* END COMPONENT: Agent */
		
	
	public static final int __ACT__send = 0;	
	public static final String __ACT_NAME__send = "send";
	public static final int __ACT__check = 1;	
	public static final String __ACT_NAME__check = "check";
	public static final int __ACT__becomeLeader = 2;	
	public static final String __ACT_NAME__becomeLeader = "becomeLeader";
	public static final int __ACT__becomeFollower = 3;	
	public static final String __ACT_NAME__becomeFollower = "becomeFollower";
	public static final int __ACT__becomeFeederz = 4;	
	public static final String __ACT_NAME__becomeFeederz = "becomeFeederz";
	public static final int __ACT__beLeader = 5;	
	public static final String __ACT_NAME__beLeader = "beLeader";
	
	
	public String[] getSystems() {
		return new String[] {
			"AllTheWayInRing"
		};	
	}
	
	public SimulationFactory<CarmaSystem> getFactory( String name ) {
		if ("AllTheWayInRing".equals( name )) {
			return getFactorySystemAllTheWayInRing();
		}
		return null;
	}
			
	
	public class __SYSTEM__AllTheWayInRing extends CarmaSystem {
		
		public __SYSTEM__AllTheWayInRing( ) {
			super( );
			Integer __ATTR__messages;
			Integer __GLOBAL__messages;
			__ATTR__messages =  0;
			__GLOBAL__messages = __ATTR__messages;
			setGLobalAttribute( "messages" , __ATTR__messages );
			CarmaSystem system = this;
			CarmaSystem sys = this;
			for( int __VARIABLE__i = 0; ( __VARIABLE__i )<( __CONST__NODEZ ) ; __VARIABLE__i = __VARIABLE__i + 1 ) {
				{
						CarmaComponent fooComponent = createComponentAgent(					
							Integer.valueOf( __VARIABLE__i )
						);
						system.addComponent( fooComponent );
				}
			}
		}
		
		@Override
		public double broadcastProbability( CarmaStore sender , CarmaStore receiver , int action ) {
			return 1.0;
		}
	
		@Override
		public double unicastProbability( CarmaStore sender , CarmaStore receiver , int action ) {
			return 1.0;
		}
		
		@Override
		public double broadcastRate( CarmaStore sender , int action ) {
			return 1.0;
		}
	
		@Override
		public double unicastRate( CarmaStore sender , int action ) {
			return 1.0;
		}				
		
		@Override
		public void broadcastUpdate( 
			final RandomGenerator random , 
			final CarmaStore sender , 
			final int action , 
			final Object value ) {
			final CarmaSystem system = this;
			final CarmaSystem sys = this;
			final CarmaStore global = this.global;
			final CarmaStore store = this.global;
			Node __SENDER__loc = sender.get( "loc" , Node.class );
		}
		
		@Override
		public void unicastUpdate( 
			final RandomGenerator random , 
			final CarmaStore sender , 
			final CarmaStore receiver, 
			int action , 
			final Object value ) {
			final CarmaSystem system = this;
			final CarmaSystem sys = this;
			final CarmaStore global = this.global;
			final CarmaStore store = this.global;
			Integer __GLOBAL__messages = (Integer) global.get( "messages" );
			Node __SENDER__loc = sender.get( "loc" , Node.class );
			if (action==__ACT__send) {
				store.set( "messages", ( __GLOBAL__messages )+( 1 ) );
				return ;				
			}
		}		
	}
	
	
	public SimulationFactory<CarmaSystem> getFactorySystemAllTheWayInRing() {
		return new SimulationFactory<CarmaSystem>() {
	
			//@Override
			public CarmaSystem getModel() {
				CarmaSystem sys = new __SYSTEM__AllTheWayInRing();
				CarmaSystem.setCurrentSpaceModel( sys.getSpaceModel() );
				return sys;
			}
		
			//@Override
			public Measure<CarmaSystem> getMeasure(String name) {
				// TODO Auto-generated method stub
				//FIXME!!!!
				return null;
			}
		
		};
		
	}
	
		
		public String[] getMeasures() {
			TreeSet<String> sortedSet = new TreeSet<String>( );
			sortedSet.add( "leader" );
			sortedSet.add( "followerz" );
			sortedSet.add( "messages" );
			sortedSet.add( "idlez" );
			sortedSet.add( "activez" );
			sortedSet.add( "checkz" );
			return sortedSet.toArray( new String[ sortedSet.size() ] );
		}
		
		public Measure<CarmaSystem> getMeasure( String name , Map<String,Object> parameters ) {
			if ("leader".equals( name ) ) {
				return getMeasureleader( parameters );
			}
			if ("followerz".equals( name ) ) {
				return getMeasurefollowerz( parameters );
			}
			if ("messages".equals( name ) ) {
				return getMeasuremessages( parameters );
			}
			if ("idlez".equals( name ) ) {
				return getMeasureidlez( parameters );
			}
			if ("activez".equals( name ) ) {
				return getMeasureactivez( parameters );
			}
			if ("checkz".equals( name ) ) {
				return getMeasurecheckz( parameters );
			}
			return null;
		}
	
		public String[] getMeasureParameters( String name ) {
			if ("leader".equals( name ) ) {
				return new String[] { };
			}
			if ("followerz".equals( name ) ) {
				return new String[] { };
			}
			if ("messages".equals( name ) ) {
				return new String[] { };
			}
			if ("idlez".equals( name ) ) {
				return new String[] { };
			}
			if ("activez".equals( name ) ) {
				return new String[] { };
			}
			if ("checkz".equals( name ) ) {
				return new String[] { };
			}
			return new String[] {};
		}
		
		public Map<String,Class<?>> getParametersType( String name ) {
			if ("leader".equals( name ) ) {
				HashMap<String,Class<?>> toReturn = new HashMap<>();
				return toReturn;
			}
			if ("followerz".equals( name ) ) {
				HashMap<String,Class<?>> toReturn = new HashMap<>();
				return toReturn;
			}
			if ("messages".equals( name ) ) {
				HashMap<String,Class<?>> toReturn = new HashMap<>();
				return toReturn;
			}
			if ("idlez".equals( name ) ) {
				HashMap<String,Class<?>> toReturn = new HashMap<>();
				return toReturn;
			}
			if ("activez".equals( name ) ) {
				HashMap<String,Class<?>> toReturn = new HashMap<>();
				return toReturn;
			}
			if ("checkz".equals( name ) ) {
				HashMap<String,Class<?>> toReturn = new HashMap<>();
				return toReturn;
			}
			return new HashMap<>();
		}
		
	
		private double __MEASURE__leader( CarmaSystem system ) {
			final CarmaStore global = system.getGlobalStore();
			final double now = system.now();
			final CarmaSystem sys = system;
			return system.measure( 
				new BasicComponentPredicate(
					new CarmaPredicate() {
						
						//Here we assume that the following "final" references are available (if needed):
						//- global: reference to the global store;
						//- sender: reference to the store of sender;
						//- receiver: reference to the store of the receiver;				
						//@Override
						public boolean satisfy(double now,CarmaStore store) {
							Node __MY__loc = store.get( "loc" , Node.class );
							try{
								Boolean result = true;
								return (result==null?false:result);
							} catch (NullPointerException e) {
								return false;
							}
						}
					
						
					}
					, new CarmaProcessPredicate() {
				
						//@Override
						public boolean eval(CarmaProcess p) {
							if (p instanceof CarmaSequentialProcess) {
								CarmaSequentialProcess csp = (CarmaSequentialProcess) p;
								try{
									return csp.getName().equals("Agent")&&csp.getState().getName().equals("LEADER");
								} catch (NullPointerException e) {
									return false;
								}
							}
							return false;
						}
									
					}
					)
			)
			;
		}
		
		
		private Measure<CarmaSystem> getMeasureleader( 
			Map<String,Object> parameters
		) {
			
		
			return new Measure<CarmaSystem>() {
			
				//@Override
				public double measure(final CarmaSystem system) {
					return __MEASURE__leader( system );
				}
		
				//@Override
				public String getName() {
					return "leader";
				}
			
			};
			
		}
		
		private double __MEASURE__followerz( CarmaSystem system ) {
			final CarmaStore global = system.getGlobalStore();
			final double now = system.now();
			final CarmaSystem sys = system;
			return system.measure( 
				new BasicComponentPredicate(
					new CarmaPredicate() {
						
						//Here we assume that the following "final" references are available (if needed):
						//- global: reference to the global store;
						//- sender: reference to the store of sender;
						//- receiver: reference to the store of the receiver;				
						//@Override
						public boolean satisfy(double now,CarmaStore store) {
							Node __MY__loc = store.get( "loc" , Node.class );
							try{
								Boolean result = true;
								return (result==null?false:result);
							} catch (NullPointerException e) {
								return false;
							}
						}
					
						
					}
					, new CarmaProcessPredicate() {
				
						//@Override
						public boolean eval(CarmaProcess p) {
							if (p instanceof CarmaSequentialProcess) {
								CarmaSequentialProcess csp = (CarmaSequentialProcess) p;
								try{
									return csp.getName().equals("Agent")&&csp.getState().getName().equals("FOLLOWER");
								} catch (NullPointerException e) {
									return false;
								}
							}
							return false;
						}
									
					}
					)
			)
			;
		}
		
		
		private Measure<CarmaSystem> getMeasurefollowerz( 
			Map<String,Object> parameters
		) {
			
		
			return new Measure<CarmaSystem>() {
			
				//@Override
				public double measure(final CarmaSystem system) {
					return __MEASURE__followerz( system );
				}
		
				//@Override
				public String getName() {
					return "followerz";
				}
			
			};
			
		}
		
		private double __MEASURE__messages( CarmaSystem system ) {
			final CarmaStore global = system.getGlobalStore();
			final double now = system.now();
			final CarmaSystem sys = system;
			Integer __GLOBAL__messages = (Integer) global.get( "messages" );
			return __GLOBAL__messages;
		}
		
		
		private Measure<CarmaSystem> getMeasuremessages( 
			Map<String,Object> parameters
		) {
			
		
			return new Measure<CarmaSystem>() {
			
				//@Override
				public double measure(final CarmaSystem system) {
					return __MEASURE__messages( system );
				}
		
				//@Override
				public String getName() {
					return "messages";
				}
			
			};
			
		}
		
		private double __MEASURE__idlez( CarmaSystem system ) {
			final CarmaStore global = system.getGlobalStore();
			final double now = system.now();
			final CarmaSystem sys = system;
			return system.measure( 
				new BasicComponentPredicate(
					new CarmaPredicate() {
						
						//Here we assume that the following "final" references are available (if needed):
						//- global: reference to the global store;
						//- sender: reference to the store of sender;
						//- receiver: reference to the store of the receiver;				
						//@Override
						public boolean satisfy(double now,CarmaStore store) {
							Node __MY__loc = store.get( "loc" , Node.class );
							try{
								Boolean result = true;
								return (result==null?false:result);
							} catch (NullPointerException e) {
								return false;
							}
						}
					
						
					}
					, new CarmaProcessPredicate() {
				
						//@Override
						public boolean eval(CarmaProcess p) {
							if (p instanceof CarmaSequentialProcess) {
								CarmaSequentialProcess csp = (CarmaSequentialProcess) p;
								try{
									return csp.getName().equals("Agent")&&csp.getState().getName().equals("IDLE");
								} catch (NullPointerException e) {
									return false;
								}
							}
							return false;
						}
									
					}
					)
			)
			;
		}
		
		
		private Measure<CarmaSystem> getMeasureidlez( 
			Map<String,Object> parameters
		) {
			
		
			return new Measure<CarmaSystem>() {
			
				//@Override
				public double measure(final CarmaSystem system) {
					return __MEASURE__idlez( system );
				}
		
				//@Override
				public String getName() {
					return "idlez";
				}
			
			};
			
		}
		
		private double __MEASURE__activez( CarmaSystem system ) {
			final CarmaStore global = system.getGlobalStore();
			final double now = system.now();
			final CarmaSystem sys = system;
			return system.measure( 
				new BasicComponentPredicate(
					new CarmaPredicate() {
						
						//Here we assume that the following "final" references are available (if needed):
						//- global: reference to the global store;
						//- sender: reference to the store of sender;
						//- receiver: reference to the store of the receiver;				
						//@Override
						public boolean satisfy(double now,CarmaStore store) {
							Node __MY__loc = store.get( "loc" , Node.class );
							try{
								Boolean result = true;
								return (result==null?false:result);
							} catch (NullPointerException e) {
								return false;
							}
						}
					
						
					}
					, new CarmaProcessPredicate() {
				
						//@Override
						public boolean eval(CarmaProcess p) {
							if (p instanceof CarmaSequentialProcess) {
								CarmaSequentialProcess csp = (CarmaSequentialProcess) p;
								try{
									return csp.getName().equals("Agent")&&csp.getState().getName().equals("ACTIVE");
								} catch (NullPointerException e) {
									return false;
								}
							}
							return false;
						}
									
					}
					)
			)
			;
		}
		
		
		private Measure<CarmaSystem> getMeasureactivez( 
			Map<String,Object> parameters
		) {
			
		
			return new Measure<CarmaSystem>() {
			
				//@Override
				public double measure(final CarmaSystem system) {
					return __MEASURE__activez( system );
				}
		
				//@Override
				public String getName() {
					return "activez";
				}
			
			};
			
		}
		
		private double __MEASURE__checkz( CarmaSystem system ) {
			final CarmaStore global = system.getGlobalStore();
			final double now = system.now();
			final CarmaSystem sys = system;
			return system.measure( 
				new BasicComponentPredicate(
					new CarmaPredicate() {
						
						//Here we assume that the following "final" references are available (if needed):
						//- global: reference to the global store;
						//- sender: reference to the store of sender;
						//- receiver: reference to the store of the receiver;				
						//@Override
						public boolean satisfy(double now,CarmaStore store) {
							Node __MY__loc = store.get( "loc" , Node.class );
							try{
								Boolean result = true;
								return (result==null?false:result);
							} catch (NullPointerException e) {
								return false;
							}
						}
					
						
					}
					, new CarmaProcessPredicate() {
				
						//@Override
						public boolean eval(CarmaProcess p) {
							if (p instanceof CarmaSequentialProcess) {
								CarmaSequentialProcess csp = (CarmaSequentialProcess) p;
								try{
									return csp.getName().equals("Agent")&&csp.getState().getName().equals("CHECK");
								} catch (NullPointerException e) {
									return false;
								}
							}
							return false;
						}
									
					}
					)
			)
			;
		}
		
		
		private Measure<CarmaSystem> getMeasurecheckz( 
			Map<String,Object> parameters
		) {
			
		
			return new Measure<CarmaSystem>() {
			
				//@Override
				public double measure(final CarmaSystem system) {
					return __MEASURE__checkz( system );
				}
		
				//@Override
				public String getName() {
					return "checkz";
				}
			
			};
			
		}
		
		
	
}
