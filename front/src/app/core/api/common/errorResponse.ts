import { throwError } from 'rxjs';

/**
 * Extracts error message from caught error and throws a new Error observable.
 * @param error The caught error.
 * @returns An Observable that emits an Error with the extracted error message.
 */
const getErrorMessageFromCatchedError = (error: any) => {
  console.log('error', error);

  // Extract error message from the error object or set a default message
  const errorMessage = error.error.message || 'An error occurred';
  return throwError(() => new Error(errorMessage));
};

export default getErrorMessageFromCatchedError;
