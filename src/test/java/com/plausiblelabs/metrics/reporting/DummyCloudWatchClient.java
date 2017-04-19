/*
 * Copyright Iconology, Inc. 2012. All rights reserved.
 */

package com.plausiblelabs.metrics.reporting;

import java.util.List;
import java.util.Map;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClient;
import com.amazonaws.services.cloudwatch.model.MetricDatum;
import com.amazonaws.services.cloudwatch.model.PutMetricDataRequest;
import com.amazonaws.services.cloudwatch.model.PutMetricDataResult;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class DummyCloudWatchClient extends AmazonCloudWatchClient {

    public final List<MetricDatum> putData = Lists.newArrayList();

    public final Map<String, MetricDatum> latestPutByName = Maps.newHashMap();

    @SuppressWarnings("deprecation")
    public DummyCloudWatchClient() { }

    @Override
    public PutMetricDataResult putMetricData(PutMetricDataRequest req) throws AmazonServiceException, AmazonClientException {
        putData.addAll(req.getMetricData());
        for (MetricDatum datum : req.getMetricData()) {
            latestPutByName.put(datum.getMetricName(), datum);
        }
        return super.putMetricData(req);
    }
}
