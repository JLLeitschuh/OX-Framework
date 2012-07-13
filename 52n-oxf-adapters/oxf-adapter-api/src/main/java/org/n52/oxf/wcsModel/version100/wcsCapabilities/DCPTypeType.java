/**
 * ﻿Copyright (C) 2012
 * by 52 North Initiative for Geospatial Open Source Software GmbH
 *
 * Contact: Andreas Wytzisk
 * 52 North Initiative for Geospatial Open Source Software GmbH
 * Martin-Luther-King-Weg 24
 * 48155 Muenster, Germany
 * info@52north.org
 *
 * This program is free software; you can redistribute and/or modify it under
 * the terms of the GNU General Public License version 2 as published by the
 * Free Software Foundation.
 *
 * This program is distributed WITHOUT ANY WARRANTY; even without the implied
 * WARRANTY OF MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program (see gnu-gpl v2.txt). If not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA or
 * visit the Free Software Foundation web page, http://www.fsf.org.
 */
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0-b11-EA 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2005.06.25 at 02:38:11 CEST 
//


package org.n52.oxf.wcsModel.version100.wcsCapabilities;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlType(name = "DCPTypeType", namespace = "http://www.opengis.net/wcs")
public class DCPTypeType {

    @XmlElement(name = "HTTP", namespace = "http://www.opengis.net/wcs", type = org.n52.oxf.wcsModel.version100.wcsCapabilities.DCPTypeType.HTTP.class)
    protected org.n52.oxf.wcsModel.version100.wcsCapabilities.DCPTypeType.HTTP http;

    /**
     * Gets the value of the http property.
     * 
     * @return
     *     possible object is
     *     {@link org.n52.oxf.wcsModel.version100.wcsCapabilities.DCPTypeType.HTTP}
     */
    public org.n52.oxf.wcsModel.version100.wcsCapabilities.DCPTypeType.HTTP getHTTP() {
        return http;
    }

    /**
     * Sets the value of the http property.
     * 
     * @param value
     *     allowed object is
     *     {@link org.n52.oxf.wcsModel.version100.wcsCapabilities.DCPTypeType.HTTP}
     */
    public void setHTTP(org.n52.oxf.wcsModel.version100.wcsCapabilities.DCPTypeType.HTTP value) {
        this.http = value;
    }

    @XmlAccessorType(value = XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class HTTP {

        @XmlElements(value = {
            @XmlElement(name = "Get", namespace = "http://www.opengis.net/wcs", type = org.n52.oxf.wcsModel.version100.wcsCapabilities.DCPTypeType.HTTP.Get.class),
            @XmlElement(name = "Post", namespace = "http://www.opengis.net/wcs", type = org.n52.oxf.wcsModel.version100.wcsCapabilities.DCPTypeType.HTTP.Post.class)
        })
        protected List<Object> getOrPost;

        protected List<Object> _getGetOrPost() {
            if (getOrPost == null) {
                getOrPost = new ArrayList<Object>();
            }
            return getOrPost;
        }

        /**
         * Gets the value of the getOrPost property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the getOrPost property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getGetOrPost().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link org.n52.oxf.wcsModel.version100.wcsCapabilities.DCPTypeType.HTTP.Get}
         * {@link org.n52.oxf.wcsModel.version100.wcsCapabilities.DCPTypeType.HTTP.Post}
         * 
         */
        public List<Object> getGetOrPost() {
            return this._getGetOrPost();
        }

        @XmlAccessorType(value = XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class Get {

            @XmlElement(name = "OnlineResource", namespace = "http://www.opengis.net/wcs", type = OnlineResourceType.class)
            protected OnlineResourceType onlineResource;

            /**
             * Gets the value of the onlineResource property.
             * 
             * @return
             *     possible object is
             *     {@link org.n52.oxf.wcsModel.version100.wcsCapabilities.OnlineResourceType}
             */
            public OnlineResourceType getOnlineResource() {
                return onlineResource;
            }

            /**
             * Sets the value of the onlineResource property.
             * 
             * @param value
             *     allowed object is
             *     {@link org.n52.oxf.wcsModel.version100.wcsCapabilities.OnlineResourceType}
             */
            public void setOnlineResource(OnlineResourceType value) {
                this.onlineResource = value;
            }

        }

        @XmlAccessorType(value = XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class Post {

            @XmlElement(name = "OnlineResource", namespace = "http://www.opengis.net/wcs", type = OnlineResourceType.class)
            protected OnlineResourceType onlineResource;

            /**
             * Gets the value of the onlineResource property.
             * 
             * @return
             *     possible object is
             *     {@link org.n52.oxf.wcsModel.version100.wcsCapabilities.OnlineResourceType}
             */
            public OnlineResourceType getOnlineResource() {
                return onlineResource;
            }

            /**
             * Sets the value of the onlineResource property.
             * 
             * @param value
             *     allowed object is
             *     {@link org.n52.oxf.wcsModel.version100.wcsCapabilities.OnlineResourceType}
             */
            public void setOnlineResource(OnlineResourceType value) {
                this.onlineResource = value;
            }

        }

    }

}