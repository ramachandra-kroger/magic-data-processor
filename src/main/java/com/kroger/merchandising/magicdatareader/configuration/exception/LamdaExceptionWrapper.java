package com.kroger.merchandising.magicdatareader.configuration.exception;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@Slf4j
public class LamdaExceptionWrapper {

   public static <T> Consumer<T> handlingConsumerWithPotentialException(ThrowableConsumer<T, Exception> throwableConsumer) {
      return throwable -> {
         try{
            throwableConsumer.accept(throwable);
         } catch (Exception e){
            log.error("Error in lamda execution", e);
            throw new RuntimeException(e);
         }
      };
   }

}
