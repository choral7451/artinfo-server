import {Args, Mutation, Resolver} from "@nestjs/graphql";
import {AuthService} from "@/apis/auth/auth.service";
import {TokensResponse} from "@/apis/auth/dto/response/tokensResponse";
import {LoginRequest} from "@/apis/auth/dto/request/login.request";
import {RestoreTokensRequest} from "@/apis/auth/dto/request/restore-tokens.request";
import {Signature} from "@/commons/decorator/signature";
import {UseGuards} from "@nestjs/common";
import {JwtAuthGuard} from "@/commons/auth/jwt-auth.guard";

@Resolver()
export class AuthResolver {
  constructor(
    private readonly authService: AuthService
  ) {}

  @Mutation(() => TokensResponse)
  async login(@Args('input') request: LoginRequest): Promise<TokensResponse> {
    const tokens = await this.authService.login(request.email, request.password)
    return TokensResponse.fromTokens(tokens.accessToken, tokens.refreshToken)
  }

  @UseGuards(JwtAuthGuard)
  @Mutation(() => TokensResponse)
  async restoreTokens(@Args('input') request: RestoreTokensRequest, @Signature() signature): Promise<TokensResponse> {
    const tokens = await this.authService.restoreTokens(signature.id, request.refreshToken)
    return TokensResponse.fromTokens(tokens.accessToken, tokens.refreshToken)
  }
}