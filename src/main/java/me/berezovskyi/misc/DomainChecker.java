package me.berezovskyi.misc;

import java.util.Optional;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.NSRecord;
import org.xbill.DNS.Record;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Type;

public class DomainChecker {
    public static void main(String[] args) {
        String host = Optional.ofNullable(args[0]).orElse("gmail.com");
        Record[] records = new Record[0];
        try {
            records = new Lookup(host, Type.NS).run();
        } catch (TextParseException e) {
            e.printStackTrace();
        }
        if (records != null) {
            for (final Record record : records) {
                NSRecord nsRecord = (NSRecord) record;
                System.out.printf("Host %s has nameserver %s%n", host, nsRecord.getTarget());
            }
        } else {
            System.err.printf("Host %s has no nameservers%n", host);
        }
    }
}
