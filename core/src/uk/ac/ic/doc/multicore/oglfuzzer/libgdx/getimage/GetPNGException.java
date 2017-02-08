package uk.ac.ic.doc.multicore.oglfuzzer.libgdx.getimage;

public class GetPNGException extends RuntimeException {

  public GetPNGException(GlErrorException ex) {
    super(ex);
  }

  @Override
  public synchronized GlErrorException getCause() {
    return (GlErrorException) super.getCause();
  }
}
