// Code generated by gobind. DO NOT EDIT.

// Java class grpc.User is a proxy for talking to a Go program.
//
//   autogenerated by gobind -lang=java GoGrpc/grpc
package grpc;

import go.Seq;

public final class User implements Seq.Proxy {
	static { Grpc.touch(); }
	
	private final int refnum;
	
	@Override public final int incRefnum() {
	      Seq.incGoRef(refnum, this);
	      return refnum;
	}
	
	public User(String usrid, String logPath, long gRPCPort, String gRPCTLSCert, String gRPCTLSKey, String gRPCCACert, GrpcDemoService service) {
		this.refnum = __NewUser(usrid, logPath, gRPCPort, gRPCTLSCert, gRPCTLSKey, gRPCCACert, service);
		Seq.trackGoRef(refnum, this);
	}
	
	private static native int __NewUser(String usrid, String logPath, long gRPCPort, String gRPCTLSCert, String gRPCTLSKey, String gRPCCACert, GrpcDemoService service);
	
	User(int refnum) { this.refnum = refnum; Seq.trackGoRef(refnum, this); }
	
	public native void run() throws Exception;
	public native void stopServer();
	@Override public boolean equals(Object o) {
		if (o == null || !(o instanceof User)) {
		    return false;
		}
		User that = (User)o;
		return true;
	}
	
	@Override public int hashCode() {
	    return java.util.Arrays.hashCode(new Object[] {});
	}
	
	@Override public String toString() {
		StringBuilder b = new StringBuilder();
		b.append("User").append("{");
		return b.append("}").toString();
	}
}
