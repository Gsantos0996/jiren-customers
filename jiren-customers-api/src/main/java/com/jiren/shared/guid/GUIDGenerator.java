package com.jiren.shared.guid;

import java.math.BigInteger;

public class GUIDGenerator {

	private final Snowflake idGeneratorAlgorithm;
    private final AlphaID idEncoder;

    public GUIDGenerator(Integer tenantNumber, Integer contextNumber) {
        idGeneratorAlgorithm = new Snowflake(getNodeId(tenantNumber, contextNumber));
        idEncoder = new AlphaID(AlphaID.DICTIONARY_62);
    }

    private static Long getNodeId(Integer tenantNumber, Integer contextNumber ) {
        Long nodeId;
        nodeId = 100L * tenantNumber.longValue() + contextNumber.longValue();
        return nodeId;
    }

    /**
     * Generate unique ID
     * @return unique ID
     */
    public synchronized BigInteger generateID() {
        return BigInteger.valueOf(idGeneratorAlgorithm.nextId());
    }
    /**
     * Generate unique alpha ID
     * @return alpha ID
     */
    public synchronized String generateAlphaID() {
        return idEncoder.encode(generateID());
    }
    /**
     * Decode unique alpha ID
     * @return unique ID
     */
    public BigInteger decodeAlphaID(String encodedId) {
        return idEncoder.decode(encodedId);
    }
    /**
     * Encode unique ID to alpha ID
     * @return alpha ID
     */
    public String encodeID(BigInteger id) {
        return idEncoder.encode(id);
    }
}
