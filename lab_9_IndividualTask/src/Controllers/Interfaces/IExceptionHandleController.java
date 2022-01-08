package Controllers.Interfaces;

/**
* The interface of apps exceptions handler class.
*/
public interface IExceptionHandleController {
    /**
     *This method will handle exception in implemented realization.
     * @param ex Exception handled by the implemented algorithm.
    */
    void HandleException(Exception ex);
}
