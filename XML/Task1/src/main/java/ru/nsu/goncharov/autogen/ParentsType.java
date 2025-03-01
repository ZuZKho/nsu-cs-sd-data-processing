
package ru.nsu.goncharov.autogen;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ParentsType complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="ParentsType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="father" type="{}FamilyConnectionType" minOccurs="0"/>
 *         <element name="mother" type="{}FamilyConnectionType" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ParentsType", propOrder = {
    "father",
    "mother"
})
public class ParentsType {

    protected FamilyConnectionType father;
    protected FamilyConnectionType mother;

    /**
     * Gets the value of the father property.
     * 
     * @return
     *     possible object is
     *     {@link FamilyConnectionType }
     *     
     */
    public FamilyConnectionType getFather() {
        return father;
    }

    /**
     * Sets the value of the father property.
     * 
     * @param value
     *     allowed object is
     *     {@link FamilyConnectionType }
     *     
     */
    public void setFather(FamilyConnectionType value) {
        this.father = value;
    }

    /**
     * Gets the value of the mother property.
     * 
     * @return
     *     possible object is
     *     {@link FamilyConnectionType }
     *     
     */
    public FamilyConnectionType getMother() {
        return mother;
    }

    /**
     * Sets the value of the mother property.
     * 
     * @param value
     *     allowed object is
     *     {@link FamilyConnectionType }
     *     
     */
    public void setMother(FamilyConnectionType value) {
        this.mother = value;
    }

}
