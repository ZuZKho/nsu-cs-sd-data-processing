
package ru.nsu.goncharov.autogen;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SiblingsType complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="SiblingsType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="sister" type="{}FamilyConnectionType" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="brother" type="{}FamilyConnectionType" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SiblingsType", propOrder = {
    "sister",
    "brother"
})
public class SiblingsType {

    protected List<FamilyConnectionType> sister;
    protected List<FamilyConnectionType> brother;

    /**
     * Gets the value of the sister property.
     * 
     * <p>This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sister property.</p>
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * </p>
     * <pre>
     * getSister().add(newItem);
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
     *     The value of the sister property.
     */
    public List<FamilyConnectionType> getSister() {
        if (sister == null) {
            sister = new ArrayList<>();
        }
        return this.sister;
    }

    /**
     * Gets the value of the brother property.
     * 
     * <p>This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the brother property.</p>
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * </p>
     * <pre>
     * getBrother().add(newItem);
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
     *     The value of the brother property.
     */
    public List<FamilyConnectionType> getBrother() {
        if (brother == null) {
            brother = new ArrayList<>();
        }
        return this.brother;
    }

}
