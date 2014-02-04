/**
 * ﻿Copyright (C) 2012-2014 52°North Initiative for Geospatial Open Source
 * Software GmbH
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License version 2 as publishedby the Free
 * Software Foundation.
 *
 * If the program is linked with libraries which are licensed under one of the
 * following licenses, the combination of the program with the linked library is
 * not considered a "derivative work" of the program:
 *
 *     - Apache License, version 2.0
 *     - Apache Software License, version 1.0
 *     - GNU Lesser General Public License, version 3
 *     - Mozilla Public License, versions 1.0, 1.1 and 2.0
 *     - Common Development and Distribution License (CDDL), version 1.0
 *
 * Therefore the distribution of the program linked with libraries licensed under
 * the aforementioned licenses, is permitted by the copyright holders if the
 * distribution is compliant with both the GNU General Public License version 2
 * and the aforementioned licenses.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU General Public License for more details.
 */
package org.n52.oxf.render.sos;

import java.util.List;

import org.n52.oxf.feature.OXFAbstractObservationType;
import org.n52.oxf.feature.OXFFeature;
import org.n52.oxf.feature.OXFMeasurementType;
import org.n52.oxf.feature.dataTypes.OXFMeasureType;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Point;

/**
 * @author <a href="mailto:broering@52north.org">Arne Broering</a>
 * 
 */
public class IDWRenderer extends InterpolationRenderer {

	//In case of not setting maxDistance use all
	private double maxInvDistance = 0;
	
    public Double computeInterpolatedValue(Coordinate coordToInterpolate,
                                           List<OXFFeature> observationList) {
        double result = 0;
        double distanceSum = 0;

        for (OXFFeature observation : observationList) {
            // TODO: the location of the FOI (associated with the observation) will be taken and not the
            // Location of the observation itself.
            if (observation.getAttribute(OXFAbstractObservationType.FEATURE_OF_INTEREST) != null) {
                OXFFeature featureOfInterest = (OXFFeature) observation.getAttribute(OXFAbstractObservationType.FEATURE_OF_INTEREST);

                // TODO Spec-Too-Flexible-Problem --> various GeometryTypes are possible:
                if (featureOfInterest.getGeometry() instanceof Point) {
                    Point foiPoint = (Point) featureOfInterest.getGeometry();

                    Coordinate coordinateOfFOI = foiPoint.getCoordinate();

                    double tmpInvDistance = 1 / coordToInterpolate.distance(coordinateOfFOI);
                    //Only use Points with distance < MaxDistance
                    if(maxInvDistance<tmpInvDistance){
	                    double tmpValue = Math.pow(tmpInvDistance, 3);
	                    distanceSum += tmpValue;
	
	                    // TODO Spec-Too-Flexible-Problem --> various FeatureTypes are possible:
	                    if (observation.getFeatureType() instanceof org.n52.oxf.feature.OXFMeasurementType) {
	                        OXFMeasureType measureResult = (OXFMeasureType) observation.getAttribute(OXFMeasurementType.RESULT);
	                        double measurement = measureResult.getValue();
	
	                        result += tmpValue * measurement;
	                    }
                    }
                }
            }
        }

        return result / distanceSum;
    }
    
    /**
     * @return a plain text description of this Renderer.
     */
    public String getDescription() {
        return "IDWRenderer - visualizes an isoline map for the selected phenomenon";
    }
    
    public String toString() {
        return getDescription();
    }
    /**
     * 
     * @param maxDistance Maximum distance between interpolatedpoint and datapoint to use it in interpolation 
     */
    public void setMaxDistance(double maxDistance){
    	maxInvDistance = 1/maxDistance;
    }
    
    /**
     * @return the type of the service whose data can be rendered with this ServiceRenderer. In this case
     *         "OGC:SOS" will be returned.
     */
    public String getServiceType() {
        return "OGC:SOS";
    }

    /**
     * @return the versions of the services whose data can be rendered with this ServiceRenderer. In this case
     *         {"1.0.0"} will be returned.
     */
    public String[] getSupportedVersions() {
        return new String[] {"0.0.0"};
    }
    
}