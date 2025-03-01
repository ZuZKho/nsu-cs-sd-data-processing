
package ru.nsu.goncharov.autogen;

import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ru.nsu.goncharov.autogen package. 
 * <p>An ObjectFactory allows you to programmatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ru.nsu.goncharov.autogen
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link People }
     * 
     * @return
     *     the new instance of {@link People }
     */
    public People createPeople() {
        return new People();
    }

    /**
     * Create an instance of {@link PersonType }
     * 
     * @return
     *     the new instance of {@link PersonType }
     */
    public PersonType createPersonType() {
        return new PersonType();
    }

    /**
     * Create an instance of {@link FamilyConnectionType }
     * 
     * @return
     *     the new instance of {@link FamilyConnectionType }
     */
    public FamilyConnectionType createFamilyConnectionType() {
        return new FamilyConnectionType();
    }

    /**
     * Create an instance of {@link SiblingsType }
     * 
     * @return
     *     the new instance of {@link SiblingsType }
     */
    public SiblingsType createSiblingsType() {
        return new SiblingsType();
    }

    /**
     * Create an instance of {@link ParentsType }
     * 
     * @return
     *     the new instance of {@link ParentsType }
     */
    public ParentsType createParentsType() {
        return new ParentsType();
    }

    /**
     * Create an instance of {@link ChildrenType }
     * 
     * @return
     *     the new instance of {@link ChildrenType }
     */
    public ChildrenType createChildrenType() {
        return new ChildrenType();
    }

}
