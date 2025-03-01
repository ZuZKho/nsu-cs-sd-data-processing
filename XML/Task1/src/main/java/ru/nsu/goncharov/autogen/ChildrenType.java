
package ru.nsu.goncharov.autogen;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ChildrenType complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="ChildrenType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="son" type="{}FamilyConnectionType" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="daughter" type="{}FamilyConnectionType" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ChildrenType", propOrder = {
    "son",
    "daughter"
})
public class ChildrenType {

    protected List<FamilyConnectionType> son;
    protected List<FamilyConnectionType> daughter;

    /**
     * Gets the value of the son property.
     * 
     * <p>This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the son property.</p>
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * </p>
     * <pre>
     * getSon().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FamilyConnectionType }
     * </p>
     * 
     * 
     * @return
     *     The value of the son property.
     */
    public List<FamilyConnectionType> getSon() {
        if (son == null) {
            son = new ArrayList<>();
        }
        return this.son;
    }

    /**
     * Gets the value of the daughter property.
     * 
     * <p>This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the daughter property.</p>
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * </p>
     * <pre>
     * getDaughter().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FamilyConnectionType }
     * </p>
     * 
     * 
     * @return
     *     The value of the daughter property.
     */
    public List<FamilyConnectionType> getDaughter() {
        if (daughter == null) {
            daughter = new ArrayList<>();
        }
        return this.daughter;
    }

}
