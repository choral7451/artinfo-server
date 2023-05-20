import {Field, ObjectType} from "@nestjs/graphql";

@ObjectType()
export class TokensResponse {
  @Field(() => String)
  accessToken: string

  @Field(() => String)
  refreshToken: string

  static fromTokens(accessToken: string, refreshToken: string): TokensResponse {
    const response = new TokensResponse()
    response.accessToken = accessToken
    response.refreshToken =  refreshToken

    return response
  }
}