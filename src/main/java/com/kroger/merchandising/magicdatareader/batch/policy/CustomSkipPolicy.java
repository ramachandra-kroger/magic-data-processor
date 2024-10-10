package com.kroger.merchandising.magicdatareader.batch.policy;

import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;


public class CustomSkipPolicy implements SkipPolicy {
    @Override
    public boolean shouldSkip(Throwable t, long skipCount) throws SkipLimitExceededException {
        //TODO- add any case where the job should be stoped
//        if(t instanceof FlatFileParseException f) {
//            return false;
//        }

        //skip all failings
        return true;


    }
}
