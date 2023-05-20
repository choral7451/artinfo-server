import {Field, InputType} from "@nestjs/graphql";

@InputType()
export class RestoreTokensRequest {
  @Field(() => String)
  refreshToken: string
}