//package tools;
//
//// $Id: dumpont2.java,v 1.2 2003/11/07 14:46:31 mdean Exp $
//
//
///**
// * print class and property hierarchies from an ontology.
// */
//class dumpont2
//{
//    static boolean html = true;
//    static String hyperdaml = "http://www.daml.org/cgi-bin/hyperdaml";
//    static boolean verbose = false;
//
//    static java.util.Stack stack = new java.util.Stack();
//
//    static java.util.Comparator comparator = new java.util.Comparator()
//    {
//	public int compare(Object o1, Object o2)
//	{
//	    return o1.toString().compareTo(o2.toString());
//	}
//	
//	public boolean equals(Object comparator)
//	{
//	    return false;
//	}
//	};
//    
//
//    /**
//     * information about a DAML Class
//     */
//    static class Class
//    {
//	static java.util.TreeMap classes = new java.util.TreeMap(comparator);
//	com.hp.hpl.jena.rdf.arp.AResource node;
//	boolean restriction = false;
//	java.util.Vector subclasses = new java.util.Vector();
//	java.util.Vector superclasses = new java.util.Vector();
//	java.util.TreeSet properties = new java.util.TreeSet(comparator);
//	java.util.TreeSet instances = new java.util.TreeSet(comparator);
//
//	Class(com.hp.hpl.jena.rdf.arp.AResource node)
//	{
//	    this.node = node;
//	    classes.put(node, this);
//	}
//
//	void print(int depth)
//	{
//	    if (restriction || node.isAnonymous())
//		return;
//
//	    String classFragment = fragment(node);
//
//	    if (html)
//		{
//		    System.out.println("<li>");
//		    System.out.println("<a name=" + quoted(classFragment) + ">");
//		}
//	    else
//		for (int i = 0; i < depth; i++)
//		    System.out.println("  ");
//	    System.out.print("<a href=" + quoted(hyperdaml + "?" + node) + ">" + classFragment + "</a>");
//
//	    // check for cycles
//	    if (stack.contains(this))
//		{
//		    System.out.println(" ... cycle ...");
//		    return;
//		}
//	    stack.push(this);
//
//	    // properties
//	    System.out.print(" (");
//	    java.util.Iterator iterator = properties.iterator();
//	    boolean first = true;
//	    while (iterator.hasNext())
//		{
//		    Object property = iterator.next();
//		    if (! first)
//			System.out.print(", ");
//		    first = false;
//		    if (html)
//			{
//			    String fragment = fragment(property);
//			    System.out.print("<a href=" + quoted("#" + fragment) + ">" + fragment + "</a>");
//			}
//		    else
//			System.out.print(property);
//			
//		}
//	    System.out.println(")");
//
//	    // recurse
//	    iterator = subclasses.iterator();
//	    java.util.Iterator instanceIterator = instances.iterator();
//	    if (iterator.hasNext() 
//		|| instanceIterator.hasNext()) 
//		{
//		    if (html)
//			System.out.println("<ul>");
//		    while (iterator.hasNext())
//			{
//			    Class child = (Class) iterator.next();
//			    child.print(depth + 1);
//			}
//		    while (instanceIterator.hasNext())
//			{
//			    String instanceFragment = fragment(instanceIterator.next());
//			    if (html)
//				{
//				    System.out.println("<li>");
//				    System.out.println("<a name=" + quoted(instanceFragment) + ">");
//				}
//			    else
//				for (int i = 0; i < depth; i++)
//				    System.out.println("  ");
//			    System.out.println("instance " + instanceFragment);
//			}
//		    if (html)
//			System.out.println("</ul>");
//		}
//
//	    stack.pop();
//	}
//
//	static Class get(com.hp.hpl.jena.rdf.arp.AResource node)
//	{
//	    Class retval = (Class) classes.get(node);
//	    if (retval == null)
//		retval = new Class(node);
//	    return retval;
//	}
//    }
//
//    /**
//     * information about a DAML Property
//     */
//    static class Property
//    {
//	static java.util.TreeMap properties = new java.util.TreeMap(comparator);
//	com.hp.hpl.jena.rdf.arp.AResource node;
//	java.util.Vector subproperties = new java.util.Vector();
//	java.util.Vector superproperties = new java.util.Vector();
//
//	Property(com.hp.hpl.jena.rdf.arp.AResource node)
//	{
//	    this.node = node;
//	    properties.put(node, this);
//	}
//
//	void print(int depth)
//	{
//	    String propertyFragment = fragment(node);
//
//	    if (html)
//		{
//		    System.out.println("<li>");
//		    System.out.println("<a name=" + quoted(propertyFragment) + ">");
//		}
//	    else
//		for (int i = 0; i < depth; i++)
//		    System.out.println("  ");
//	    System.out.print("<a href=" + quoted(hyperdaml + "?" + node) + ">" + propertyFragment + "</a>");
//
//	    // check for cycles
//	    if (stack.contains(this))
//		{
//		    System.out.println(" ... cycle ...");
//		    return;
//		}
//	    stack.push(this);
//
//	    System.out.println();
//
//	    // recurse
//	    java.util.Iterator iterator = subproperties.iterator();
//	    if (iterator.hasNext()) 
//		{
//		    if (html)
//			System.out.println("<ul>");
//		    while (iterator.hasNext())
//			{
//			    Property child = (Property) iterator.next();
//			    child.print(depth + 1);
//			}
//		    if (html)
//			System.out.println("</ul>");
//		}
//
//	    stack.pop();
//	}
//
//	public String toString()
//	{
//	    return node.getURI();
//	}
//
//	static Property get(com.hp.hpl.jena.rdf.arp.AResource node)
//	{
//	    Property retval = (Property) properties.get(node);
//	    if (retval == null)
//		retval = new Property(node);
//	    return retval;
//	}
//    }
//
//    static String quoted(String string)
//    {
//	return '"' + string + '"';
//    }
//
//    static String fragment(Object object)
//    {
//	if (object == null)
//	    return null;
//	String uri = object.toString();
//	int pound = uri.lastIndexOf('#');
//	return (pound == (-1)) ? uri : uri.substring(pound + 1);
//    }
//
//    static void printHeader(String header)
//    {
//	if (html)
//	    System.out.print("<h2>");
//	System.out.print(header);
//	if (html)
//	    System.out.print("</h2>");
//	System.out.println();
//    }
//
//    static void warn(String warning)
//    {
//	if (verbose)
//	    System.err.println(warning);
//    }
//
//    static void usage()
//    {
//	System.err.println("Usage:  [-verbose] [-hyperdaml uri] <daml-uri>");
//	System.exit(1);
//    }
//
//    public static void main(String args[])
//	throws Exception
//    {
//	// parse arguments
//	String daml = null;
//	for (int i = 0; i < args.length; i++)
//	    {
//		String arg = args[i];
//
//		if (arg.charAt(0) == '-')
//		    {
//			if (arg.equals("-verbose"))
//			    verbose = true;
//			else if (arg.equals("-hyperdaml"))
//			    {
//				if ((i+1) > args.length)
//				    usage();
//				hyperdaml = args[++i];
//			    }
//			else
//			    usage();
//		    }
//		else
//		    daml = arg;
//	    }
//	if (daml == null)
//	    usage();
//
//	// strip form input prefix
//	if (daml.startsWith("uri="))
//	    daml = java.net.URLDecoder.decode(daml.substring(4));
//
//	// collect ontology information while streaming input
//	String rdf = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
//	String rdfs = "http://www.w3.org/2000/01/rdf-schema#";
//
//	final java.util.HashSet propertyTypes = new java.util.HashSet();
//	propertyTypes.add("Property");
//	propertyTypes.add("DatatypeProperty");
//	propertyTypes.add("ObjectProperty");
//	propertyTypes.add("TransitiveProperty");
//	propertyTypes.add("SymmetricProperty");
//	propertyTypes.add("UnambiguousProperty");
//	propertyTypes.add("UniqueProperty");
//	propertyTypes.add("FunctionalProperty");
//	propertyTypes.add("InverseFunctionalProperty");
//	propertyTypes.add("AnnotationProperty");
//	propertyTypes.add("OntologyProperty");
//	propertyTypes.add("DeprecatedProperty");
//
//	final java.util.HashSet otherTypes = new java.util.HashSet();
//	otherTypes.add("Ontology");
//	otherTypes.add("List");
//	otherTypes.add("DataRange");
//
//	com.hp.hpl.jena.rdf.arp.ARP arp = new com.hp.hpl.jena.rdf.arp.ARP();
//	arp.setStatementHandler(new com.hp.hpl.jena.rdf.arp.StatementHandler()
//	    {
//		public void statement(com.hp.hpl.jena.rdf.arp.AResource subj,
//				      com.hp.hpl.jena.rdf.arp.AResource pred,
//				      com.hp.hpl.jena.rdf.arp.AResource obj)
//		{
//		    String subject = subj.getURI();
//		    String predicate = pred.getURI();
//		    String object = obj.getURI();
//
//		    String predicateFragment = fragment(predicate);
//		    String objectFragment = fragment(object);
//
//		    if (predicateFragment.equals("type")) // XXX - only rdf:type?
//			{
//			    if (objectFragment.equals("Class"))
//				{
//				    Class.get(subj);
//				}
//			    else if (objectFragment.equals("Restriction"))
//				{
//				    Class cl = Class.get(subj);
//				    cl.restriction = true;
//				}
//			    else if (propertyTypes.contains(objectFragment))
//				{
//				    Property.get(subj);
//				}
//			    else if (otherTypes.contains(objectFragment))
//				{
//				    // nothing
//				}
//			    else
//				{
//				    Class cl = Class.get(obj);
//				    cl.instances.add(subj);
//				}
//			}
//		    else if (predicateFragment.equals("subClassOf"))
//			{
//			    Class parent = Class.get(obj);
//			    Class child = Class.get(subj);
//			    parent.subclasses.add(child);
//			    child.superclasses.add(parent);
//			}
//		    else if (predicateFragment.equals("subPropertyOf"))
//			{
//			    Property parent = Property.get(obj);
//			    Property child = Property.get(subj);
//			    parent.subproperties.add(child);
//			    child.superproperties.add(parent);
//			}
//		    else if (predicateFragment.equals("domain"))
//			{
//			    Class cl = Class.get(obj);
//			    Property property = Property.get(subj);
//			    cl.properties.add(property);
//			}
//		    else if (predicateFragment.equals("onProperty"))
//			{
//			    Class cl = Class.get(subj);
//			    cl.restriction = true;
//			    Property property = Property.get(obj);
//			    cl.properties.add(property);
//			}
//		}
//
//		public void statement(com.hp.hpl.jena.rdf.arp.AResource subj,
//				      com.hp.hpl.jena.rdf.arp.AResource pred,
//				      com.hp.hpl.jena.rdf.arp.ALiteral obj)
//		{
//		    // nothing
//		}
//	    });
//	java.io.InputStream stream = new java.net.URL(daml).openStream();
//	arp.load(stream, daml);
//
//	// sort
//	java.util.Collection sortedClasses = Class.classes.values();
//	java.util.Collection sortedProperties = Property.properties.values();
//
//	// propagate properties from Restrictions
//	java.util.Iterator iterator = sortedClasses.iterator();
//	while (iterator.hasNext())
//	    {
//		Class cl = (Class) iterator.next();
//		if (cl.restriction)
//		    {
//			java.util.Iterator subclasses = cl.subclasses.iterator();
//			while (subclasses.hasNext())
//			    {
//				Class subclass = (Class) subclasses.next();
//
//				subclass.properties.addAll(cl.properties);
//				subclass.superclasses.remove(cl);
//			    }
//		    }
//	    }
//
//	// print Classes
//	iterator = sortedClasses.iterator();
//	printHeader("Class Hierarchy");
//	if (html)
//	    {
//		System.out.println("<ul>");
//	    }
//	while (iterator.hasNext())
//	    {
//		Class cl = (Class) iterator.next();
//		if (cl.superclasses.size() == 0) // root
//		    cl.print(1);
//	    }
//	if (html)
//	    System.out.println("</ul>");
//
//	// print Properties
//	iterator = sortedProperties.iterator();
//	printHeader("Property Hierarchy");
//	if (html)
//	    {
//		System.out.println("<ul>");
//	    }
//	while (iterator.hasNext())
//	    {
//		Property property = (Property) iterator.next();
//		if (property.superproperties.size() == 0) // root
//		    property.print(1);
//	    }
//	if (html)
//	    System.out.println("</ul>");
//
//	// trailer
//	if (html)
//	    {
//		System.out.println("<hr>");
//		System.out.println("<address>");
//		System.out.println("Produced from ");
//		System.out.println("<a href=" + quoted(daml) + ">" + daml + "</a> using <a href=" + quoted("http://www.daml.org/2003/09/dumpont/") + ">dumpont2</a>.java");
//		System.out.println("</address>");
//	    }
//    }
//}
