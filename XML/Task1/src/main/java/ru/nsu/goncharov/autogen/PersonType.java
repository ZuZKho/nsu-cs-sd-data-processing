
package ru.nsu.goncharov.autogen;

import java.math.BigInteger;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for PersonType complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="PersonType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="wife" type="{}FamilyConnectionType" minOccurs="0"/>
 *         <element name="husband" type="{}FamilyConnectionType" minOccurs="0"/>
 *         <element name="parents" type="{}ParentsType" minOccurs="0"/>
 *         <element name="siblings" type="{}SiblingsType" minOccurs="0"/>
 *         <element name="children" type="{}ChildrenType" minOccurs="0"/>
 *       </sequence>
 *       <attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *       <attribute name="gender" type="{}GenderType" />
 *       <attribute name="firstName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       <attribute name="lastName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       <attribute name="childrenNumber" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       <attribute name="siblingsNumber" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonType", propOrder = {
    "wife",
    "husband",
    "parents",
    "siblings",
    "children"
})
public class PersonType {

    protected FamilyConnectionType wife;
    protected FamilyConnectionType husband;
    protected ParentsType parents;
    protected SiblingsType siblings;
    protected ChildrenType children;
    @XmlAttribute(name = "id", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;
    @XmlAttribute(name = "gender")
    protected GenderType gender;
    @XmlAttribute(name = "firstName")
    protected String firstName;
    @XmlAttribute(name = "lastName")
    protected String lastName;
    @XmlAttribute(name = "childrenNumber")
    protected BigInteger childrenNumber;
    @XmlAttribute(name = "siblingsNumber")
    protected BigInteger siblingsNumber;

    /**
     * Gets the value of the wife property.
     * 
     * @return
     *     possible object is
     *     {@link FamilyConnectionType }
     *     
     */
    public FamilyConnectionType getWife() {
        return wife;
    }

    /**
     * Sets the value of the wife property.
     * 
     * @param value
     *     allowed object is
     *     {@link FamilyConnectionType }
     *     
     */
    public void setWife(FamilyConnectionType value) {
        this.wife = value;
    }

    /**
     * Gets the value of the husband property.
     * 
     * @return
     *     possible object is
     *     {@link FamilyConnectionType }
     *     
     */
    public FamilyConnectionType getHusband() {
        return husband;
    }

    /**
     * Sets the value of the husband property.
     * 
     * @param value
     *     allowed object is
     *     {@link FamilyConnectionType }
     *     
     */
    public void setHusband(FamilyConnectionType value) {
        this.husband = value;
    }

    /**
     * Gets the value of the parents property.
     * 
     * @return
     *     possible object is
     *     {@link ParentsType }
     *     
     */
    public ParentsType getParents() {
        return parents;
    }

    /**
     * Sets the value of the parents property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParentsType }
     *     
     */
    public void setParents(ParentsType value) {
        this.parents = value;
    }

    /**
     * Gets the value of the siblings property.
     * 
     * @return
     *     possible object is
     *     {@link SiblingsType }
     *     
     */
    public SiblingsType getSiblings() {
        return siblings;
    }

    /**
     * Sets the value of the siblings property.
     * 
     * @param value
     *     allowed object is
     *     {@link SiblingsType }
     *     
     */
    public void setSiblings(SiblingsType value) {
        this.siblings = value;
    }

    /**
     * Gets the value of the children property.
     * 
     * @return
     *     possible object is
     *     {@link ChildrenType }
     *     
     */
    public ChildrenType getChildren() {
        return children;
    }

    /**
     * Sets the value of the children property.
     * 
     * @param value
     *     allowed object is
     *     {@link ChildrenType }
     *     
     */
    public void setChildren(ChildrenType value) {
        this.children = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the gender property.
     * 
     * @return
     *     possible object is
     *     {@link GenderType }
     *     
     */
    public GenderType getGender() {
        return gender;
    }

    /**
     * Sets the value of the gender property.
     * 
     * @param value
     *     allowed object is
     *     {@link GenderType }
     *     
     */
    public void setGender(GenderType value) {
        this.gender = value;
    }

    /**
     * Gets the value of the firstName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the value of the firstName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstName(String value) {
        this.firstName = value;
    }

    /**
     * Gets the value of the lastName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the value of the lastName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastName(String value) {
        this.lastName = value;
    }

    /**
     * Gets the value of the childrenNumber property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getChildrenNumber() {
        return childrenNumber;
    }

    /**
     * Sets the value of the childrenNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setChildrenNumber(BigInteger value) {
        this.childrenNumber = value;
    }

    /**
     * Gets the value of the siblingsNumber property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSiblingsNumber() {
        return siblingsNumber;
    }

    /**
     * Sets the value of the siblingsNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSiblingsNumber(BigInteger value) {
        this.siblingsNumber = value;
    }

}
