package com.shamith.springbatch;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.policy.Policy;
import com.aerospike.client.policy.WritePolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CacheService {

    @Autowired
    private AerospikeClient aerospikeClient;


        public void saveToCache() {
            System.out.println("Caching Started");
            WritePolicy writePolicy = new WritePolicy();
            writePolicy.expiration = 5 * 60; // 5 minutes
            Key key = new Key("loadtest", "set1", "name");
            Bin bin1 = new Bin("bin", "Pauls");
            aerospikeClient.put(writePolicy, key, bin1);
            System.out.println("Stored");


            // let's try to get the same data from cache
            Policy policy = new Policy();
            Record record = aerospikeClient.get(policy, key);
            if(record !=null){
                Map<String, Object> bins = record.bins;
                System.out.println("Getting data");
                System.out.println(bins);
            }




        }


}
