import {ObjectType} from "@nestjs/graphql";

@ObjectType()
export class SuccessResponse {
  success: boolean;

  static fromResult(result: boolean): SuccessResponse {
    const response = new SuccessResponse();
    response.success = result;
    return response;
  }
}
